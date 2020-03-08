package site.yan.httpclient.constant;

public enum HttpClientPairType {
    PATH("path"),
    STATUS_CODE("status code"),
    CONTENT_SIZE("content size");

    private final String value;

    HttpClientPairType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }
}
