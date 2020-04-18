package site.yan.core.enumeration;

/**
 * HTTP 方法常量
 */
public enum HttpMethodType {
    POST("post"),
    GET("get");

    private final String value;

    HttpMethodType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }
}
