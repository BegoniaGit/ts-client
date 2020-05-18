package site.yan.core.api.filter;

import site.yan.core.api.protocol.HttpRequest;
import site.yan.core.api.protocol.HttpResponse;

/**
 * The {@code HttpProcessFilter} interface is a process.
 * <p>Full implementation of it can achieve more http processing<p/>
 *
 * @author zhao xubin
 * @date 2020-2-29
 */
public interface HttpProcessFilter {

    /**
     * http pre processing
     *
     * @param httpRequest
     * @param httpResponse
     * @param filterContext
     */
    void preProcess(HttpRequest httpRequest, HttpResponse httpResponse, FilterContext filterContext);

    /**
     * http post processing
     *
     * @param httpRequest
     * @param httpResponse
     * @param filterContext
     */
    void postProcess(HttpRequest httpRequest, HttpResponse httpResponse, FilterContext filterContext);

}
