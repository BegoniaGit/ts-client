package site.yan.web.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import site.yan.core.adapter.Holder;
import site.yan.core.adapter.HttpContextAdapter;
import site.yan.core.cache.TraceCache;
import site.yan.core.configer.TSProperties;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.HeaderType;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.interceptor.TsInterceptor;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.RandomUtil;
import site.yan.core.utils.TimeUtil;
import site.yan.web.constant.TraceIgnoreType;
import site.yan.web.constant.WebFilterPairType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class TSWebFilter implements Filter, TsInterceptor {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 判断是否需要拦截
        if (traceIgnore(servletRequest)) {
            doTsFilter(servletRequest, servletResponse, filterChain);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doTsFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Record record = RecordContextHolder.getCurrentServerRecord();
        // 处理前操作
        HttpContextAdapter httpContextAdapter = toHolder(servletRequest, servletResponse, filterChain);
        tsPre(httpContextAdapter);
        HttpServletResponse response = httpContextAdapter.getHttpServletResponse();
        response.addHeader(HeaderType.TS_ID.text(), record.getId());
        response.addHeader(HeaderType.TS_TRACE_ID.text(), record.getTraceId());
        // 异常检查
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            httpContextAdapter.setHasException(false);
        } catch (ServletException exc) {
            httpContextAdapter.setHasException(true);
            httpContextAdapter.setException(exc);
            throw exc;
        } catch (IOException exc2) {
            httpContextAdapter.setHasException(true);
            httpContextAdapter.setException(exc2);
            throw exc2;
        } finally {
            // 处理后操作
            tsPost(httpContextAdapter);
        }
    }

    private HttpContextAdapter toHolder(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpContextAdapter httpContextAdapter = new HttpContextAdapter(servletRequest, servletResponse, filterChain);
        return httpContextAdapter;
    }

    @Override
    public Holder tsPre(Holder holder) {
        HttpContextAdapter httpContextAdapter = (HttpContextAdapter) holder;
        // 获取当前 HTTP 服务顶级 Trace Record
        Record record = RecordContextHolder.getCurrentServerRecord();
        String traceId = httpContextAdapter.isNewTrace() ? IdGeneratorHelper.idLen32Generat() : httpContextAdapter.getTraceId();
        String parentId = httpContextAdapter.getId();

        record.setTraceId(traceId)
                .setParentId(parentId)
                .setIdNotLastId(IdGeneratorHelper.idLen32Generat())
                .setName(httpContextAdapter.getName())
                .setStartTimeStamp(TimeUtil.stamp())
                .setServerName(TSProperties.getServerName())
                .setStage(TSProperties.getStage());
        return httpContextAdapter;
    }

    @Override
    public void tsPost(Holder holder) {
        HttpContextAdapter httpContextAdapter = (HttpContextAdapter) holder;
        long currentStamp = TimeUtil.stamp();
        Record record = RecordContextHolder.getCurrentServerRecord();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());
        record.setError(httpContextAdapter.isHasException());
        Host clientHost = new Host(httpContextAdapter.getLastServerName(), httpContextAdapter.getRemoteAddress(), httpContextAdapter.getRemotePort());
        Note startNote = new Note(NoteType.SERVER_RECEIVE.text(), record.getStartTimeStamp(), clientHost);
        Host serverHost = new Host(TSProperties.getServerName(), httpContextAdapter.getLocalAddress(), httpContextAdapter.getLocalPort());
        Note endNote = new Note(NoteType.SERVER_RESPONSE.text(), currentStamp, serverHost);

        record.addNotePair(startNote, endNote);

        final String contentSize = httpContextAdapter.getHttpServletResponse().getHeader("content-Length");
        record.putAdditionalPair(WebFilterPairType.PATH.text(), httpContextAdapter.getPath());
        record.putAdditionalPair(WebFilterPairType.STATUS_CODE.text(), httpContextAdapter.isHasException() ? "500" : String.valueOf(httpContextAdapter.getHttpServletResponse().getStatus()));
        record.putAdditionalPair(WebFilterPairType.CONTENT_SIZE.text(), Strings.isBlank(contentSize) ? "0" : contentSize);
        if (httpContextAdapter.isHasException()) {
            record.putAdditionalPair(WebFilterPairType.EXCEPTION.text(), httpContextAdapter.getException().getMessage());
        }
        TraceCache.put(new Record(record));
        record.clear();
        RecordContextHolder.lastIdGetAndSet(null);
    }

    private boolean traceIgnore(ServletRequest request) {
        String open = ((HttpServletRequest) request).getHeader(HeaderType.TS_TRACE_OPEN.text());
        if (open != null) {
            return "true".equals(open);
        }
        if (RandomUtil.getRandom().nextDouble() < TSProperties.getSamplingRate()) {
            RecordContextHolder.updateCurrentRecordState(true);
        } else {
            RecordContextHolder.updateCurrentRecordState(false);
            return false;
        }
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String path = null;
        try {
            path = new URL(servletRequest.getRequestURL().toString()).getPath();
        } catch (MalformedURLException e) {
            path = "";
        }
        for (String item : TraceIgnoreType.ignoreUrl) {
            if (path.startsWith(item) || path.endsWith(item)) {
                return false;
            }
        }
        for (String item : TSProperties.getTraceIgnoreUrls()) {
            if (path.startsWith(item) || path.endsWith(item)) {
                return false;
            }
        }
        return true;
    }
}
