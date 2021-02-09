package connection;

import constants.MessageConstants;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {
    private final static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool instance = null;
    private static final String TOMCAT_JNDI_NAME = "java:comp/env";
    private DataSource pool;
    private final String DATASOURCE;

    public ConnectionPool() {
        DATASOURCE ="source";
        initialPool();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initialPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(TOMCAT_JNDI_NAME);
            pool = (DataSource) envContext.lookup(DATASOURCE);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(MessageConstants.STATEMENT_ERROR);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(MessageConstants.RESULTSET_ERROR);
            }
        }
    }
}
