package site.yan.web.filter;

import org.springframework.stereotype.Component;
import site.yan.core.adapter.WebFilterAdapter;
import site.yan.core.data.Record;
import site.yan.core.trace.Trace;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TSWebFilter extends WebFilterAdapter {

    private Trace trace;
    private Record record;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Trace trace= new Trace();
        record=trace.getRecord();
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void destroy() {

    }


}