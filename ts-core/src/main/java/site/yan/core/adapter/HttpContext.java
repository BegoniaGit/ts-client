package site.yan.core.adapter;

import org.apache.logging.log4j.util.Strings;
import site.yan.core.enumeration.HeaderType;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpContext implements Holder {

    private boolean hasException;
    private Exception exception;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private ServletRequest servletRequest;
    private ServletResponse servletResponse;
    private FilterChain filterChain;

    public HttpContext(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        this.httpServletRequest = (HttpServletRequest) servletRequest;
        this.httpServletResponse = (HttpServletResponse) servletResponse;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.filterChain = filterChain;
        this.hasException=false;
    }

    public boolean isHasException() {
        return hasException;
    }

    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getName() {
        return "http." + this.httpServletRequest.getMethod().toLowerCase();
    }

    public String getTraceId() {
        return this.httpServletRequest.getHeader(HeaderType.TS_TRACE_ID.text());
    }

    public String getId() {
        return this.httpServletRequest.getHeader(HeaderType.TS_ID.text());
    }

    public String getLastServerName() {
        String lastServerName = this.httpServletRequest.getHeader(HeaderType.TS_SERVER_NAME.text());
        return Strings.isBlank(lastServerName) ? "front end user" : lastServerName;
    }

    public boolean isNewTrace() {
        return Strings.isBlank(getTraceId());
    }

    public String getPath() {
        String path = null;
        try {
            path = new URL(this.httpServletRequest.getRequestURL().toString()).getPath();
        } catch (MalformedURLException e) {
            path = "";
        } finally {
            return path;
        }
    }

    public String getRemoteAddress() {
        return httpServletRequest.getRemoteAddr();
    }

    public int getRemotePort() {
        return httpServletRequest.getRemotePort();
    }

    public String getLocalAddress() {
        return httpServletRequest.getLocalAddr();
    }

    public int getLocalPort() {
        return httpServletRequest.getLocalPort();
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    public void setServletResponse(ServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    public FilterChain getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }
}

