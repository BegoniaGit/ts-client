package site.yan.core.api.protocol;

/**
 * @author zhao xubin
 * @date 2020-3-28
 */
public interface HttpRequest {

    void parse(byte[] rawByte);

    String getMethod();

    String getPath();

    String getProtocol();
}
