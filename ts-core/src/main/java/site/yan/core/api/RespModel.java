package site.yan.core.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespModel<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public RespModel() {
        // Nothing to do.
    }

    public RespModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RespModel ok(Object data) {
        return new RespModel(0, "ok", data);
    }

    public static RespModel error(String msg) {
        return new RespModel(-1, msg, null);
    }

    public static RespModel error() {
        return new RespModel(-1, "An unknown error has occurred.", null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
