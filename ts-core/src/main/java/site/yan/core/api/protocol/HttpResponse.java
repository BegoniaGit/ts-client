package site.yan.core.api.protocol;

/**
 * @author zhao xubin
 * @date 2020-3-28
 */
public interface HttpResponse {
    void addHeader(String name, Object value);

    byte[] createBytes();
}
