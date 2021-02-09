package exceptions;

import java.sql.SQLException;

public class DAOException extends Exception {

    public DAOException(String message) {
    }

    public DAOException(String message, SQLException e) {
    }
}
