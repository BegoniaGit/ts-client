package site.yan.core.helper;

import site.yan.core.data.Host;
import site.yan.core.data.LastId;
import site.yan.core.data.Pack;
import site.yan.core.data.Record;
import site.yan.core.utils.StringUtil;

import java.util.Objects;

/**
 * 该状态类保存了当前 http 服务基本信息，子 record 可以从此类获取该有的信息
 */
public class RecordContextHolder {

    private static final ThreadLocal<Record> CURRENT_SERVER_RECORD = new ThreadLocal<Record>() {
        @Override
        protected Record initialValue() {
            return new Record(true);
        }
    };
    private static final ThreadLocal<Pack<Boolean>> CURRENT_OPEN = new ThreadLocal<Pack<Boolean>>() {
        @Override
        protected Pack<Boolean> initialValue() {
            return Pack.create(true);
        }
    };
    private static final ThreadLocal<LastId> LAST_ID = new ThreadLocal<LastId>() {
        @Override
        protected LastId initialValue() {
            return new LastId(null);
        }
    };

    private static Host host;

    private static String stage;

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

    public static Boolean getCurrentOpenState() {
        return CURRENT_OPEN.get().getValue();
    }

    public static void updateCurrentRecordState(boolean state) {
        CURRENT_OPEN.get().update(state);
    }

    public String getServerRecordId() {
        return CURRENT_SERVER_RECORD.get().getId();
    }

    public static boolean hasLastId() {
        return StringUtil.isNotBlank(RecordContextHolder.LAST_ID.get().getLastId());
    }

    public static String lastIdGetAndSet(String id) {

        String lastId = RecordContextHolder.LAST_ID.get().getLastId();
        RecordContextHolder.LAST_ID.get().setLastId(id);

        System.out.println("lastId: " + lastId);
        System.out.println("id:     " + id);
        System.out.println();
        return lastId;
    }

    public void clear() {
        CURRENT_SERVER_RECORD.remove();
    }
}
