package site.yan.core.interceptor;

import site.yan.core.adapter.Holder;

/**
 * The {@code TsInterceptor} is the normative convention for intercepting code.
 *
 * @author zhao xubin
 * @date 2020-3-7
 */
public interface TsInterceptor {

    /**
     * Pre-interception processing
     *
     * @param holder Intermediate variable holding
     * @return @{code Holder}
     */
    Holder tsPre(Holder holder);

    /**
     * Post-interception processing
     *
     * @param holder
     */
    void tsPost(Holder holder);

}
