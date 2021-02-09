package connection;

import exceptions.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler {

    public static void runInTransaction(Transaction transaction, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try {
            transaction.execute(connection);
            connection.commit();
        } catch (SQLException | DAOException e) {
            connection.rollback();
            throw new SQLException(e);
        } finally {
            connection.close();
        }
    }
}
