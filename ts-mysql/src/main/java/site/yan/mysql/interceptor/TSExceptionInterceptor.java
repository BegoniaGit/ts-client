package site.yan.mysql.interceptor;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.yan.core.data.Record;
import site.yan.core.delayed.RecordStash;
import site.yan.mysql.constant.MysqlPairType;

import java.util.Properties;

import static site.yan.mysql.interceptor.TSQueryInterceptor.mysqlRecord;

public class TSExceptionInterceptor implements ExceptionInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TSExceptionInterceptor.class);

    @Override
    public ExceptionInterceptor init(Properties props, Log log) {
        LOG.info("exception init success" + Thread.currentThread().getId());
        return new TSExceptionInterceptor();
    }

    @Override
    public void destroy() {
    }

    @Override
    public Exception interceptException(Exception sqlEx) {
        LOG.error("has error", sqlEx);
        Record record = RecordStash.getById(mysqlRecord.get().getId()).orElse(new Record(true));
        record.putAdditionalPair(MysqlPairType.EXCEPTION.text(), sqlEx.getMessage());
        record.setError(true);
        return null;
    }
}
