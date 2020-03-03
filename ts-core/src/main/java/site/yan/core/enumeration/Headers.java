package site.yan.core.enumeration;

public enum Headers {
    TS_TRACE_ID,
    TS_ID;

    public String text() {
        return this.name().replace('_', '-');
    }
}
