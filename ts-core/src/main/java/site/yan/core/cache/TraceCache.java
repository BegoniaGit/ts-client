package site.yan.core.cache;

import site.yan.core.data.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TraceCache {

    private static volatile List<Record> traceTemp;

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

    public static synchronized void put(Record record) {
        getTraceTemp().add(record);
    }

    public static synchronized List<Record> getTraceRecord() {
        List traceTemp = getTraceTemp();
        List result = new ArrayList(traceTemp);
        traceTemp.clear();
        return result;
    }
}
