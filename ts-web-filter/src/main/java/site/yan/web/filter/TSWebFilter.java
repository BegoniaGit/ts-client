package site.yan.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.yan.core.adapter.HttpRequestAdapter;
import site.yan.core.adapter.WebFilterAdapter;
import site.yan.core.cache.TraceCache;
import site.yan.core.configer.Properties;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.data.RecordBuilder;
import site.yan.core.enumeration.Headers;
import site.yan.core.enumeration.NoteType;
import site.yan.core.trace.Trace;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.TimeStamp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TSWebFilter extends WebFilterAdapter {

    private Trace trace;
    private Record record;
    private HttpRequestAdapter adapter;
    private ServletResponse response;

    @Autowired
    Properties properties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void before(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        Trace trace = new Trace();
        record = trace.getRecord();
        this.response = servletResponse;

        this.adapter = new HttpRequestAdapter(servletRequest);
        if (adapter.isNewTrace()) {
            record = new RecordBuilder()
                    .setTraceId(IdGeneratorHelper.idLen32Generat())
                    .setId(IdGeneratorHelper.idLen32Generat())
                    .setName(adapter.getName())
                    .setStartTimeStamp(TimeStamp.stamp())
                    .setServerName(properties.getServerName())
                    .setStage(properties.getStage())
                    .build();

            ((HttpServletResponse)servletResponse).addHeader(Headers.TS_ID.text(),record.getId());
            ((HttpServletResponse)servletResponse).addHeader(Headers.TS_TRACE_ID.text(),record.getTraceId());
        }else {

            String traceId=((HttpServletRequest)servletRequest).getHeader(Headers.TS_TRACE_ID.text());
            String patternId=((HttpServletRequest)servletRequest).getHeader(Headers.TS_ID.text());
            record=new RecordBuilder()
                    .setTraceId(traceId)
                    .setId(IdGeneratorHelper.idLen32Generat())
                    .setParentId(patternId)
                    .setName(adapter.getName())
                    .setStartTimeStamp(TimeStamp.stamp())
                    .setServerName(properties.getServerName())
                    .setStage(properties.getStage())
                    .build();
        }
    }

    @Override
    public void after(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        record.setDurationTime(TimeStamp.minusLastStamp(record.getStartTimeStamp()));
        NotePairGen();
        additionalPairGen();
        TraceCache.put(this.record);
    }

    private void NotePairGen() {
        Host host = new Host(properties.getServerName(), adapter.getRemoteAddress(), adapter.getRemotePort());
        Note note = new Note();
        note.setNoteName(NoteType.SERVER_RECEIVE.text());
        note.setHost(host);
        this.record.getNotePair().add(note);

        Host host2 = new Host(properties.getServerName(), adapter.getLocalAddress(), adapter.getLocalPort());
        Note note2 = new Note();
        note2.setNoteName(NoteType.SERVER_RESPONSE.text());
        note2.setHost(host2);
        this.record.getNotePair().add(note2);
    }

    private void additionalPairGen() {
        Map<String,String> additonalPair = this.record.getAdditionalPair();
        additonalPair.put("path", adapter.getPath());
        additonalPair.put("code", String.valueOf(((HttpServletResponse) this.response).getStatus()));
        additonalPair.put("responseSize",String.valueOf(((HttpServletResponse) this.response).getBufferSize()));
    }

}