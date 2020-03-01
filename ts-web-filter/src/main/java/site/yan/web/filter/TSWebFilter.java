package site.yan.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TSWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("exe start :" +System.currentTimeMillis());
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("exe end :" +System.currentTimeMillis());
    }

    @Override
    public void destroy() {

    }
}