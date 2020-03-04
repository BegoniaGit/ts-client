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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

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

        Enumeration<String> hes = ((HttpServletRequest) servletRequest).getHeaderNames();
        HttpContext httpContext = new HttpContext(servletRequest, servletResponse, filterChain);

        if (httpContext.isNewTrace()) {
            record.setTraceId(IdGeneratorHelper.idLen32Generat())
                    .setId(IdGeneratorHelper.idLen32Generat())
                    .setName(httpContext.getName())
                    .setStartTimeStamp(TimeStamp.stamp())
                    .setServerName(properties.getServerName())
                    .setStage(properties.getStage());

        } else {
            String traceId = ((HttpServletRequest) servletRequest).getHeader(HeaderType.TS_TRACE_ID.text());
            String patternId = ((HttpServletRequest) servletRequest).getHeader(HeaderType.TS_ID.text());
            record.setTraceId(traceId)
                    .setId(IdGeneratorHelper.idLen32Generat())
                    .setParentId(patternId)
                    .setName(httpContext.getName())
                    .setStartTimeStamp(TimeStamp.stamp())
                    .setServerName(properties.getServerName())
                    .setStage(properties.getStage());
        }
        return httpContext;
    }

    @Override
    public void after(HttpContext httpContext) {
        Record record = RecordContextHolder.getCurrentServerRecord();
        record.setDurationTime(TimeStamp.minusLastStamp(RecordContextHolder.getCurrentServerRecord().getStartTimeStamp()));

        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_ID.text(), record.getId());
        httpContext.getHttpServletResponse().addHeader(HeaderType.TS_TRACE_ID.text(), record.getTraceId());

        NotePairGen(httpContext);
        additionalPairGen(httpContext);
        TraceCache.put(new Record(record));


    }

    private void NotePairGen(HttpContext httpContext) {
        Record record = RecordContextHolder.getCurrentServerRecord();

        Host host = new Host(properties.getServerName(), httpContext.getRemoteAddress(), httpContext.getRemotePort());
        Note note = new Note();
        note.setNoteName(NoteType.SERVER_RECEIVE.text());
        note.setHost(host);
        record.getNotePair().add(note);

        Host serverHost = new Host(properties.getServerName(), httpContext.getLocalAddress(), httpContext.getLocalPort());
        Note note2 = new Note();
        note2.setNoteName(NoteType.SERVER_RESPONSE.text());
        note2.setHost(serverHost);
        record.getNotePair().add(note2);

        RecordContextHolder.setHost(serverHost);
    }

    private void additionalPairGen(HttpContext httpContext) {
        Record record = RecordContextHolder.getCurrentServerRecord();
        Map<String, String> additonalPair = record.getAdditionalPair();
        final String contentSize = httpContext.getHttpServletResponse().getHeader("content-Length");
        additonalPair.put("path", httpContext.getPath());
        additonalPair.put("code", String.valueOf(httpContext.getHttpServletResponse().getStatus()));
        additonalPair.put("content size", Strings.isBlank(contentSize) ? "0" : contentSize);
    }
}
