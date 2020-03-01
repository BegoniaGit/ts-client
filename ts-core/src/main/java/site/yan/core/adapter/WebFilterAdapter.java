package site.yan.core.adapter;

import javax.servlet.*;
import java.io.IOException;

public abstract class WebFilterAdapter implements Filter {

    public abstract void before();

    public abstract void after();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        before();
        filterChain.doFilter(servletRequest,servletResponse);
        after();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
