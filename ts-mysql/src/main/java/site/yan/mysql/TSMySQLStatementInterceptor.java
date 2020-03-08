package site.yan.mysql;


import com.mysql.cj.MysqlConnection;
import com.mysql.cj.Query;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.function.Supplier;

@Component
public class TSMySQLStatementInterceptor implements QueryInterceptor {

    @Override
    public <M extends Message> M preProcess(M queryPacket) {
        return null;
    }

    @Override
    public <M extends Message> M postProcess(M queryPacket, M originalResponsePacket) {
        return null;
    }

    @Override
    public QueryInterceptor init(MysqlConnection mysqlConnection, Properties properties, Log log) {
        return null;
    }

    @Override
    public <T extends Resultset> T preProcess(Supplier<String> supplier, Query query) {
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public <T extends Resultset> T postProcess(Supplier<String> supplier, Query query, T t, ServerSession serverSession) {
        return null;
    }
}