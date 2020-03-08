package site.yan.core.helper;

import site.yan.core.data.Host;
import site.yan.core.data.Record;

import java.util.Objects;

public class RecordContextHolder {

    private static final ThreadLocal<Record> CURRENT_SERVER_RECORD = new ThreadLocal<Record>() {
        protected Record initialValue() {
            return new Record();
        }
    };

    private static String parentId;

    private static Host host;

    private static String stage;

    public static String getParentId() {
        return parentId;
    }

    public static void setParentId(String parentId) {
        RecordContextHolder.parentId = parentId;
    }

    public static String getStage() {
        return stage;
    }

    public static void setStage(String stage) {
        RecordContextHolder.stage = stage;
    }

    public static String getServerName() {
        return host.getServerName();
    }

    public static String getServerAddress() {
        return host.getAddress();
    }

    public static int getServerPort() {
        return host.getPort();
    }

    public static Host getHost() {
        if (Objects.isNull(host)) {
            return null;
        } else {
            return host;
        }
    }

    public static void setHost(Host host) {
        RecordContextHolder.host = host;
    }

    public static String getTraceId() {
        return CURRENT_SERVER_RECORD.get().getTraceId();
    }

    public static String getServiceId() {
        return CURRENT_SERVER_RECORD.get().getId();
    }

    public static Record getCurrentServerRecord() {
        return CURRENT_SERVER_RECORD.get();
    }

    public String getServerRecordId() {
        return CURRENT_SERVER_RECORD.get().getId();
    }

    public void clear() {
        CURRENT_SERVER_RECORD.remove();
    }
}
