package site.yan.httpclient.model;

public class ClientResp {
    private String respStr;

    private int code;
    private long contentSize;
    private String id;
    private Exception exception;

    public ClientResp() {
        // Nothing to do
    }

    public ClientResp(String respStr, int code, long contentSize,String id) {
        this.respStr = respStr;
        this.code = code;
        this.contentSize = contentSize;
        this.id=id;
    }

    public static ClientResp BuildException(Exception exception) {
        ClientResp clientResp = new ClientResp();
        clientResp.setCode(500);
        clientResp.setException(exception);
        return clientResp;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getRespStr() {
        return respStr;
    }

    public void setRespStr(String respStr) {
        this.respStr = respStr;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
