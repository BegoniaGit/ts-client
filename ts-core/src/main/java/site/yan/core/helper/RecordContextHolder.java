package site.yan.core.helper;

import site.yan.core.data.Host;
import site.yan.core.data.Record;

public class RecordContextHolder {

    private static final ThreadLocal<Record> CURRENT_SERVER_RECORD = new ThreadLocal<Record>() {
        protected Record initialValue() {
            return new Record();
        }
    };

    private static final ThreadLocal<Host> CURRENT_SERVER_HOST = new ThreadLocal<Host>() {
        protected Host initialValue() {
            return new Host();
        }
    };

    public static void setHost(Host host) {
        Host host1 = CURRENT_SERVER_HOST.get();
        host1.setServerName(host.getServerName());
        host1.setAddress(host.getAddress());
        host1.setPort(host.getPort());
    }

    public static Host getHost() {
        return CURRENT_SERVER_HOST.get();
    }

    public static String getTraceId() {
        return ((Record) CURRENT_SERVER_RECORD.get()).getTraceId();
    }

    public static String getServiceId() {
        return ((Record) CURRENT_SERVER_RECORD.get()).getId();
    }

    public String getServerRecordId() {
        return CURRENT_SERVER_RECORD.get().getId();
    }

    public static Record getCurrentServerRecord() {
        return CURRENT_SERVER_RECORD.get();
    }

    public void clear() {
        CURRENT_SERVER_RECORD.remove();
    }
}
