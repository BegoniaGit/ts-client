package site.yan.local.constant;

public enum LocalPairType {
    ARGS("args"),
    EXCEPTION("exception"),
    METHOD_NAME("method name"),
    RETURN_TYPE("return type"),
    RETURN_VALUE("return value"),
    DESCRIPTION("description");
    private final String value;

    LocalPairType(String s) {
        this.value = s;
    }

    public String text() {
        return this.value;
    }

}
