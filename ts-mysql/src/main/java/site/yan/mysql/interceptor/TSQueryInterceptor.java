package site.yan.mysql.interceptor;


import com.mysql.cj.MysqlConnection;
import com.mysql.cj.Query;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import site.yan.core.cache.TraceCache;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeStamp;
import site.yan.mysql.constant.MysqlPairType;

import java.util.Properties;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class TSQueryInterceptor implements QueryInterceptor {

    private static final String[] filterRegex = {"set autocommit", "commit",};
    private static final String DEFAULT_SERVICE_NAME = "mysql.";
    private static final String SELECT = "select";
    private static final String UPDATE = "update";
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";

    private static ThreadLocal<Record> mysqlRecord = new ThreadLocal<Record>() {
        protected Record initialValue() {
            return new Record();
        }
    };

    @Override
    public QueryInterceptor init(MysqlConnection conn, Properties props, Log log) {
        return new TSQueryInterceptor();
    }

    @Override
    public <T extends Resultset> T preProcess(Supplier<String> sql, Query interceptedQuery) {
        if (!intercept(sql)) return null;

        mysqlRecord.get().setTraceId(RecordContextHolder.getTraceId())
                .setParentId(RecordContextHolder.getServiceId())
                .setServerName(RecordContextHolder.getServerName())
                .setStage(RecordContextHolder.getStage())
                .setStartTimeStamp(TimeStamp.stamp())
                .setName(DEFAULT_SERVICE_NAME + getTypeName(sql) + ":" + sql.get());
        return null;
    }

    @Override
    public <T extends Resultset> T postProcess(Supplier<String> sql, Query interceptedQuery, T originalResultSet, ServerSession serverSession) {
        if (!intercept(sql)) return null;
        Record record = mysqlRecord.get();
        long currentTime = TimeStamp.stamp();
        String mysqlServerName = interceptedQuery.getSession().getHostInfo().getHostProperties().get("serverName");
        record.setDurationTime(currentTime - record.getStartTimeStamp());
        HostInfo hostInfo = interceptedQuery.getSession().getHostInfo();
        Host mysqlHost = new Host(mysqlServerName, hostInfo.getHost(), hostInfo.getPort());
        Note startNote = new Note(NoteType.CLIENT_SEND.text(), record.getStartTimeStamp(), RecordContextHolder.getHost());
        Note endNote = new Note(NoteType.CLIENT_RECEIVE.text(), currentTime, mysqlHost);
        record.addNotePair(startNote, endNote);
        record.putAdditionalPair(MysqlPairType.SQL.text(), sql.get());
        record.putAdditionalPair(MysqlPairType.RESULT_TYPE.text(), interceptedQuery.getResultType().name());
        record.putAdditionalPair(MysqlPairType.RESULT_SIZE.text(), String.valueOf(originalResultSet.getRows().size()));
        record.putAdditionalPair(MysqlPairType.MYSQL_SERVER_NAME.text(), mysqlServerName);

        Record recordCopy = new Record(record);
        TraceCache.put(recordCopy);
        record.clear();
        return null;
    }


    private boolean intercept(Supplier<String> sql) {
        if (sql.get() == null) {
            return false;
        }
        for (String regex : filterRegex) {
            String lowCase = sql.get().toLowerCase();
            if (Pattern.compile(regex).matcher(lowCase).find()) {
                return false;
            }
        }
        return true;
    }

    private String getTypeName(Supplier<String> sql) {
        String sqlText = sql.get();
        if (sqlText == null) return "unknown";
        String queryType = sqlText.trim().substring(0, 6).toLowerCase();
        if (SELECT.equals(queryType)) {
            return SELECT;
        } else if (UPDATE.equals(queryType)) {
            return UPDATE;
        } else if (DELETE.equals(queryType)) {
            return DELETE;
        } else if (INSERT.equals(queryType)) {
            return INSERT;
        } else {
            return "unknown";
        }
    }

    @Override
    public boolean executeTopLevelOnly() {
        return true;
    }

    @Override
    public void destroy() {

    }
}