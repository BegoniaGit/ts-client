package site.yan.core.data;

public interface NoteType {

    public final static String SERVER_RECEIVE = "server receive";
    public final static String SERVER_RESPONSE = "server abort";
    public final static String CLIENT_SEND = "client send";
    public final static String CLIENT_RECEIVE = "client receive";
    public final static String LOCAL_START = "local start";
    public final static String LOCAL_END = "local end";
}
