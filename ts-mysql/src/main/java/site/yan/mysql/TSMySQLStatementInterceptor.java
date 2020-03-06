package site.yan.mysql;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementInterceptorV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

public class TSMySQLStatementInterceptor implements StatementInterceptorV2  {

    private static final Logger LOGGER = LoggerFactory.getLogger(TSMySQLStatementInterceptor.class);

    /*
     * @see com.mysql.jdbc.StatementInterceptorV2#destroy()
     */

    public TSMySQLStatementInterceptor() {
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /*
     * @see com.mysql.jdbc.StatementInterceptorV2#executeTopLevelOnly()
     */
    @Override
    public boolean executeTopLevelOnly() {
        // TODO Auto-generated method stub
        return true;
    }


    /*
     * @see com.mysql.jdbc.StatementInterceptorV2#init(com.mysql.jdbc.Connection,
     * java.util.Properties)
     */
    @Override
    public void init(Connection arg0, Properties arg1) throws SQLException {
        LOGGER.info("执行sql:" + arg0);
        // TODO Auto-generated method stub
    }

    /*
     * @see com.mysql.jdbc.StatementInterceptorV2#postProcess(java.lang.String,
     * com.mysql.jdbc.Statement, com.mysql.jdbc.ResultSetInternalMethods,
     * com.mysql.jdbc.Connection, int, boolean, boolean, java.sql.SQLException)
     */
    @Override
    public ResultSetInternalMethods postProcess(String arg0, Statement arg1, ResultSetInternalMethods arg2,
                                                Connection arg3, int arg4, boolean arg5, boolean arg6, SQLException arg7) throws SQLException {
        LOGGER.info("执行sql:" + arg0);
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @see com.mysql.jdbc.StatementInterceptorV2#preProcess(java.lang.String,
     * com.mysql.jdbc.Statement, com.mysql.jdbc.Connection)
     */
    @Override
    public ResultSetInternalMethods preProcess(String arg0, Statement arg1, Connection arg2) throws SQLException {
        LOGGER.info("执行sql:" + arg0);
        // TODO Auto-generated method stub
        return null;
    }
}