package site.yan.core.enumeration;

public enum HeaderType {
    TS_TRACE_ID("ts-trace-id"),
    TS_ID("ts-id"),
    TS_PARENT_ID("ts-parent-id"),
    TS_SERVER_NAME("ts-server-name");

    private final String value;

    HeaderType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }
}
