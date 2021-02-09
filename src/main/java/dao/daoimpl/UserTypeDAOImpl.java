package dao.daoimpl;

import connection.ConnectionPool;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.interfacesdao.UserTypeDAO;
import entities.UserType;
import exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserTypeDAOImpl implements UserTypeDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAOImpl.class);

    private volatile static UserTypeDAOImpl instance;

    private UserTypeDAOImpl() {
    }

    public static UserTypeDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserTypeDAOImpl.class) {
                if (instance == null) {
                    instance = new UserTypeDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(UserType userType, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_USER_TYPE);
            statement.setString(1, userType.getUserType());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void update(UserType userType, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_USER_TYPE_BY_ID);
            statement.setString(1, userType.getUserType());
            statement.setString(2, userType.getUserTypeId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(int id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.DELETE_USER_TYPE_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public UserType getById(int id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserType userType = new UserType();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return userType;
    }

    @Override
    public UserType getByType(String type, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserType userType = new UserType();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_TYPE);
            statement.setString(1, type);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return userType;
    }

    @Override
    public List<UserType> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserType> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS_TYPE);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUserType(resultSet, new UserType()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return users;
    }

    private UserType createUserType(ResultSet resultSet, UserType userType) throws SQLException {
        userType.setUserTypeId(resultSet.getInt(Parameters.USER_TYPE_ID));
        userType.setUserType(resultSet.getString(Parameters.USER_TYPE));
        return userType;
    }
}