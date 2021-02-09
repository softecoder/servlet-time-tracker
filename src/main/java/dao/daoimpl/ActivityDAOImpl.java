package dao.daoimpl;

import connection.ConnectionPool;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.interfacesdao.ActivityDAO;
import entities.Activity;
import exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAOImpl implements ActivityDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAOImpl.class);

    private volatile static ActivityDAOImpl instance;

    private ActivityDAOImpl() {
    }

    public static ActivityDAOImpl getInstance() {
        if (instance == null) {
            synchronized (ActivityDAOImpl.class) {
                if (instance == null) {
                    instance = new ActivityDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Activity getByName(String name, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Activity activity = new Activity();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createActivity(resultSet, activity);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return activity;
    }

    @Override
    public void add(Activity activity, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_ACTIVITY);
            statement.setString(1, activity.getActivityName());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void update(Activity activity, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_ACTIVITY_BY_ID);
            statement.setString(1, activity.getActivityName());
            statement.setString(2, activity.getActivityId().toString());
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
            statement = connection.prepareStatement(QueriesDB.DELETE_ACTIVITY_BY_ID);
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
    public Activity getById(String  id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Activity activity = new Activity();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createActivity(resultSet, activity);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return activity;
    }

    @Override
    public List<Activity> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Activity> activities = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_ACTIVITIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                activities.add(createActivity(resultSet, new Activity()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return activities;
    }

    @Override
    public Activity createActivity(ResultSet resultSet, Activity activity) throws SQLException {
        activity.setActivityId(resultSet.getInt(Parameters.ACTIVITY_ID_DB));
        activity.setActivityName(resultSet.getString(Parameters.ACTIVITY_NAME_DB));
        return activity;
    }

    @Override
    public boolean checkUniqueActivity(String activityName, Connection connection) throws DAOException {
        boolean isUniqueActivity = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_NAME);
            statement.setString(1, activityName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueActivity = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueActivity;
    }

    @Override
    public boolean checkUniqueActivityByUser(String id, String userId, Connection connection) throws DAOException {
        boolean isUniqueActivity = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_USER_FROM_TRACKING);
            statement.setString(1, id);
            statement.setString(2, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueActivity = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueActivity;
    }
}