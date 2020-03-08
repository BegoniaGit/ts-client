package site.yan.web.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.yan.core.adapter.HttpContext;
import site.yan.core.adapter.WebFilterAdapter;
import site.yan.core.cache.TraceCache;
import site.yan.core.configer.Properties;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.HeaderType;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.TimeStamp;
import site.yan.web.constant.WebFilterPairType;

import javax.servlet.*;

@Component
public class TSWebFilter extends WebFilterAdapter {

    @Autowired
    Properties properties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public HttpContext before(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

        Record record = RecordContextHolder.getCurrentServerRecord();

        HttpContext httpContext = new HttpContext(servletRequest, servletResponse, filterChain);

        String traceId = httpContext.isNewTrace() ? IdGeneratorHelper.idLen32Generat() : httpContext.getTraceId();
        String parentId = httpContext.getId();

        record.setTraceId(traceId)
                .setParentId(parentId)
                .setId(IdGeneratorHelper.idLen32Generat())
                .setName(httpContext.getName())
                .setStartTimeStamp(TimeStamp.stamp())
                .setServerName(properties.getServerName())
                .setStage(properties.getStage());
        return httpContext;
    }

    @Override
    public void after(HttpContext httpContext) {
        long currentStamp = TimeStamp.stamp();
        Record record = RecordContextHolder.getCurrentServerRecord();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());

        Host clientHost = new Host(httpContext.getLastServerName(), httpContext.getRemoteAddress(), httpContext.getRemotePort());
        Note startNote = new Note(NoteType.SERVER_RECEIVE.text(), record.getStartTimeStamp(), clientHost);
        Host serverHost = new Host(properties.getServerName(), httpContext.getLocalAddress(), httpContext.getLocalPort());
        Note endNote = new Note(NoteType.SERVER_RESPONSE.text(), currentStamp, serverHost);

        record.addNotePair(startNote, endNote);

        final String contentSize = httpContext.getHttpServletResponse().getHeader("content-Length");
        record.putAdditionalPair(WebFilterPairType.PATH.text(), httpContext.getPath());
        record.putAdditionalPair(WebFilterPairType.STATUS_CODE.text(), String.valueOf(httpContext.getHttpServletResponse().getStatus()));
        record.putAdditionalPair(WebFilterPairType.CONTENT_SIZE.text(), Strings.isBlank(contentSize) ? "0" : contentSize);

        TraceCache.put(new Record(record));

        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_ID.text(), record.getId());
        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_TRACE_ID.text(), record.getTraceId());
    }
}
