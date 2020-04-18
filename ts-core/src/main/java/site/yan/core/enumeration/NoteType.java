package site.yan.core.enumeration;

/**
 * record 类型常量
 */
public enum NoteType {
    SERVER_RECEIVE,
    SERVER_RESPONSE,
    CLIENT_SEND,
    CLIENT_RECEIVE,
    LOCAL_START,
    LOCAL_END;
    public String text() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
