package site.yan.core.adapter;

import org.apache.logging.log4j.util.Strings;

import site.yan.core.enumeration.Headers;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestAdapter {

    private HttpServletRequest httpServletRequest;

    public HttpRequestAdapter(ServletRequest servletRequest) {
        this.httpServletRequest = (HttpServletRequest) servletRequest;
    }

    public String getName() {
        return "HTTP." + this.httpServletRequest.getMethod();
    }

    public String getTraceId() {
        return this.httpServletRequest.getHeader(Headers.TS_TRACE_ID.name());
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


}
