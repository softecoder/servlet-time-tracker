package dao.daoimpl;

import connection.ConnectionPool;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.interfacesdao.UserDAO;
import entities.User;
import entities.UserType;
import exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    private volatile static UserDAOImpl instance;

    private UserDAOImpl() {
    }

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) {
                if (instance == null) {
                    instance = new UserDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(User user, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_NEW_USER_CLIENT);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(5, 2);
            statement.setBoolean(6, user.getRequestAdd());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public boolean isAuthorized(String login, String password, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isLogined = false;
        try {
            statement = connection.prepareStatement(QueriesDB.CHECK_AUTHORIZATION);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isLogined = true;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isLogined;
    }

    @Override
    public void update(User user, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_CLIENT_BY_ID);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(5, 2);
            statement.setBoolean(6, user.getRequestAdd());
            statement.setInt(7, user.getUserId());
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
            statement = connection.prepareStatement(QueriesDB.DELETE_USER_BY_ID);
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
    public User getByLogin(String login, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return user;
    }

    @Override
    public User getById(String id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return user;
    }

    @Override
    public List<User> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet, new User()));
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

    public User createUser(ResultSet resultSet, User user) throws SQLException {
        user.setUserId(resultSet.getInt(Parameters.USER_ID_DB));
        user.setFirstName(resultSet.getString(Parameters.FIRST_NAME_DB));
        user.setSurName(resultSet.getString(Parameters.SURNAME_DB));
        user.setLogin(resultSet.getString(Parameters.LOGIN));
        user.setPassword(resultSet.getString(Parameters.PASSWORD));
        user.setUserType(new UserType(resultSet.getInt(Parameters.USER_TYPE_ID_DB)
                , resultSet.getString(Parameters.USER_TYPE_NAME_DB)));
        user.setRequestAdd(resultSet.getBoolean(Parameters.REQUEST));
        return user;
    }

    @Override
    public boolean checkUniqueUser(String login, Connection connection) throws DAOException {
        boolean isUniqueUser = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueUser = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueUser;
    }
}