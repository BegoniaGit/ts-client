package site.yan.core.cache;

import site.yan.core.data.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TraceCache {

    // 此变量多线程共享
    private static volatile List<Record> traceTemp;

    /**
     * 双重盘空单例模式
     *
     * @return single traceTemp
     */
    public static List<Record> getTraceTemp() {
        if (Objects.isNull(traceTemp)) {
            synchronized (TraceCache.class) {
                if (Objects.isNull(traceTemp)) {
                    traceTemp = new ArrayList<>(5 * 1024);
                }
            }
        }
        return traceTemp;
    }

    /**
     * 外部调用，存入追踪记录
     *
     * @param record
     */
    public static synchronized void put(Record record) {
        getTraceTemp().add(record);
    }

    /**
     * 用于主动上报数据记录，并删除 traceTemp 中的记录
     *
     * @return traceTemp copy
     */
    public static synchronized List<Record> getTraceRecord() {
        List traceTemp = getTraceTemp();
        List result = new ArrayList(traceTemp);
        traceTemp.clear();
        return result;
    }
}
