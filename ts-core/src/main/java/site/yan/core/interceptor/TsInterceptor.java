package site.yan.core.interceptor;

import site.yan.core.adapter.Holder;
import site.yan.core.adapter.HttpContextAdapter;

/**
 * The {@code TsInterceptor} class
 *
 * @author zhao xubin
 * @date 2020-4-7
 */
public interface TsInterceptor {
    Holder tsPre(Holder holder);

    void tsPost(Holder holder);

}
