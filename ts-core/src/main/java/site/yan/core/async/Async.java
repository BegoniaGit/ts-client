package site.yan.core.async;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import site.yan.core.helper.RecordContextHolder;

public abstract class Async extends Thread{

    private String traceId;
    private String parentId;
    private String serverName;

    public void Async(){
        this.traceId = RecordContextHolder.getTraceId();
        this.parentId = RecordContextHolder.getServiceId();
        this.serverName = RecordContextHolder.getServerName();
    }

    public void task(){

    }

    @Override
     public void run() {
        task();
    }
}
