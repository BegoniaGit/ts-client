package site.yan.core.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import site.yan.core.configer.Properties;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class WebFilterAdapter implements Filter {

    @Autowired
    Properties properties;

    private final static String[] traceIgnore = {"/trace"};

    protected abstract HttpContext before(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain);

    protected abstract void after(HttpContext httpContext);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isNotTraceIgnore(servletRequest)) {
            doWebFilter(servletRequest, servletResponse, filterChain);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private void doWebFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpContext context = before(servletRequest, servletResponse, filterChain);
        filterChain.doFilter(servletRequest, servletResponse);
        after(context);
    }

    private boolean isNotTraceIgnore(ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String path = null;
        try {
            path = new URL(servletRequest.getRequestURL().toString()).getPath();
        } catch (MalformedURLException e) {
            path = "";
        }
        for (String item : traceIgnore) {
            if (item.equals(path)) {
                return false;
            }
        }
        return true;
    }
}
