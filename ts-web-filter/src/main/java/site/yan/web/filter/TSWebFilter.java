package site.yan.web.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import site.yan.core.adapter.HttpContext;
import site.yan.core.cache.TraceCache;
import site.yan.core.configer.TSProperties;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.HeaderType;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.TimeStamp;
import site.yan.web.constant.TraceIgnoreType;
import site.yan.web.constant.WebFilterPairType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class TSWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (traceIgnore(servletRequest)) {
            doTsFilter(servletRequest, servletResponse, filterChain);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doTsFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpContext context = before(servletRequest, servletResponse, filterChain);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            context.setHasException(false);
        } catch (Exception exc) {
            context.setHasException(true);
            context.setException(exc);
            throw exc;
        } finally {
            after(context);
        }
    }

    private HttpContext before(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

        Record record = RecordContextHolder.getCurrentServerRecord();

        HttpContext httpContext = new HttpContext(servletRequest, servletResponse, filterChain);

        String traceId = httpContext.isNewTrace() ? IdGeneratorHelper.idLen32Generat() : httpContext.getTraceId();
        String parentId = httpContext.getId();

        record.setTraceId(traceId)
                .setParentId(parentId)
                .setId(IdGeneratorHelper.idLen32Generat())
                .setName(httpContext.getName())
                .setStartTimeStamp(TimeStamp.stamp())
                .setServerName(TSProperties.getServerName())
                .setStage(TSProperties.getStage());
        return httpContext;
    }

    private void after(HttpContext httpContext) {
        long currentStamp = TimeStamp.stamp();
        Record record = RecordContextHolder.getCurrentServerRecord();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());
        record.setError(httpContext.isHasException());
        Host clientHost = new Host(httpContext.getLastServerName(), httpContext.getRemoteAddress(), httpContext.getRemotePort());
        Note startNote = new Note(NoteType.SERVER_RECEIVE.text(), record.getStartTimeStamp(), clientHost);
        Host serverHost = new Host(TSProperties.getServerName(), httpContext.getLocalAddress(), httpContext.getLocalPort());
        Note endNote = new Note(NoteType.SERVER_RESPONSE.text(), currentStamp, serverHost);

        record.addNotePair(startNote, endNote);

        final String contentSize = httpContext.getHttpServletResponse().getHeader("content-Length");
        record.putAdditionalPair(WebFilterPairType.PATH.text(), httpContext.getPath());
        record.putAdditionalPair(WebFilterPairType.STATUS_CODE.text(), httpContext.isHasException() ? "500" : String.valueOf(httpContext.getHttpServletResponse().getStatus()));
        record.putAdditionalPair(WebFilterPairType.CONTENT_SIZE.text(), Strings.isBlank(contentSize) ? "0" : contentSize);
        if (httpContext.isHasException()) {
            record.putAdditionalPair(WebFilterPairType.EXCEPTION.text(), httpContext.getException().getMessage());
        }
        TraceCache.put(new Record(record));
        record.clear();

        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_ID.text(), record.getId());
        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_TRACE_ID.text(), record.getTraceId());
    }

    private boolean traceIgnore(ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String path = null;
        try {
            path = new URL(servletRequest.getRequestURL().toString()).getPath();
        } catch (MalformedURLException e) {
            path = "";
        }
        for (String item : TraceIgnoreType.ignoreUrl) {
            if (item.equals(path)) {
                return false;
            }
        }
        return true;
    }
}
