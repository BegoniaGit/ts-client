package site.yan.core.enumeration;

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
