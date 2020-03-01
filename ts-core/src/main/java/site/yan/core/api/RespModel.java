package site.yan.core.api;

public class RespModel<T> {

    private int code;
    private String msg;
    private T data;

    public RespModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RespModel ok(Object data) {
        return new RespModel(0, "ok", data);
    }

    public static RespModel error(String msg){
        return new RespModel(-1,msg,null);
    }

    public static RespModel error(){
        return new RespModel(-1,"An unknown error has occurred.",null);
    }
}
