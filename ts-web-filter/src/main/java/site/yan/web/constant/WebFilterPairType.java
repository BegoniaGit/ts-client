package site.yan.web.constant;

public enum WebFilterPairType {
    PATH("path"),
    STATUS_CODE("status code"),
    CONTENT_SIZE("content size");

    private final String value;

    WebFilterPairType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }
}
