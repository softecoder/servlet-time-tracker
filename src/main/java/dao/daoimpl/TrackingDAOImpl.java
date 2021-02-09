package dao.daoimpl;

import connection.ConnectionPool;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.interfacesdao.TrackingDAO;
import entities.*;
import exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackingDAOImpl implements TrackingDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    private volatile static TrackingDAOImpl instance;

    private TrackingDAOImpl() {
    }

    public static TrackingDAOImpl getInstance() {
        if (instance == null) {
            synchronized (TrackingDAOImpl.class) {
                if (instance == null) {
                    instance = new TrackingDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void deleteTrackingByUserId(int id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.DELETE_TRACKING_BY_USER_ID);
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
    public void deleteTrackingById(Integer id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.DELETE_TRACKING_BY_ID);
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
    public Tracking getTrackingById(String id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tracking tracking = new Tracking();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_TRACKING_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createTracking(resultSet, tracking);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return tracking;
    }

    private Tracking createTracking(ResultSet resultSet, Tracking tracking) throws SQLException {
        tracking.setTrackingId(resultSet.getInt(Parameters.TRACKING_ID_DB));
        tracking.setUser(UserDAOImpl.getInstance().createUser(resultSet, new User()));
        tracking.setActivity(ActivityDAOImpl.getInstance().createActivity(resultSet, new Activity()));
        switch (resultSet.getString(Parameters.STATUS_NAME_DB)) {
            case Parameters.NEW_ACTIVITY_DB:
                tracking.setStatus(ActivityStatus.NEW_ACTIVITY);
                break;
            case Parameters.IN_PROGRESS_DB:
                tracking.setStatus(ActivityStatus.IN_PROGRESS);
                break;
            case Parameters.PAUSE:
                tracking.setStatus(ActivityStatus.PAUSE);
                break;
            case Parameters.FINISHED:
                tracking.setStatus(ActivityStatus.FINISHED);
                break;
            case Parameters.STOP:
                tracking.setStatus(ActivityStatus.STOP);
                break;
        }
        String userRequest = resultSet.getString(Parameters.USER_REQUEST_NAME_DB);
        if (!resultSet.wasNull()) {
            switch (userRequest) {
                case Parameters.ADD:
                    tracking.setUserRequest(UserRequest.ADD);
                    break;
                case Parameters.REMOVE:
                    tracking.setUserRequest(UserRequest.REMOVE);
                    break;
            }
        } else {
            tracking.setUserRequest(null);
        }
        tracking.setElapsedTime(resultSet.getString(Parameters.TIME));
        tracking.setTimeStart(resultSet.getLong(Parameters.TIME_START_DB));
        tracking.setTimeStop(resultSet.getLong(Parameters.TIME_STOP_DB));
        tracking.setDifferenceTime(resultSet.getLong(Parameters.DIFFERENCE_TIME_DB));
        tracking.setTimeSwitch(resultSet.getBoolean(Parameters.TIME_SWITCH_DB));
        return tracking;
    }

    @Override
    public void updateTrackingStatusAndTimeByID(Tracking tracking, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_TIME_BY_ID);
            statement.setString(1, tracking.getStatus().toString());
            statement.setString(2, tracking.getElapsedTime());
            statement.setString(3, tracking.getTrackingId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void updateTrackingById(String id, Tracking tracking, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING);
            Integer status = null;
            switch (tracking.getStatus().toString().toLowerCase()) {
                case Parameters.NEW_ACTIVITY_DB:
                    status = 1;
                    break;
                case Parameters.IN_PROGRESS_DB:
                    status = 2;
                    break;
                case Parameters.PAUSE:
                    status = 3;
                    break;
                case Parameters.FINISHED:
                    status = 4;
                    break;
                case Parameters.STOP:
                    status = 5;
                    break;
            }
            Integer userRequest = null;
            if (tracking.getUserRequest() != null) {
                switch (tracking.getUserRequest().toString().toLowerCase()) {
                    case Parameters.ADD:
                        userRequest = 1;
                        break;
                    case Parameters.REMOVE:
                        userRequest = 2;
                        break;
                }
                statement.setInt(2, userRequest);
            } else{
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(1, status);
            statement.setString(3, tracking.getElapsedTime());
            statement.setString(4, tracking.getTimeStart().toString());
            statement.setString(5, tracking.getTimeStop().toString());
            statement.setString(6, tracking.getDifferenceTime().toString());
            statement.setBoolean(7, tracking.isTimeSwitch());
            statement.setString(8, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } catch (NullPointerException e) {
            logger.error(MessageConstants.NULLPOINTEREXEPTIONS, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void updateTrackingStatusByID(Tracking tracking, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_BY_ID);
            statement.setString(1, tracking.getStatus().toString());
            statement.setString(3, tracking.getTrackingId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    @Override
    public void add(Tracking tracking, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_TRACKING);
            statement.setString(1, String.valueOf(tracking.getUser().getUserId()));
            statement.setString(2, String.valueOf(tracking.getActivity().getActivityId()));
            statement.setString(3, "1");
            statement.setNull(4, Types.VARCHAR);
            statement.setString(5, String.valueOf(tracking.getElapsedTime()));
            statement.setString(6, "0");
            statement.setString(7, "0");
            statement.setString(8, "0");
            statement.setBoolean(9, tracking.isTimeSwitch());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } catch (NullPointerException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }
    @Override
    public List<Tracking> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tracking> tracking = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_TRACKING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tracking.add(createTracking(resultSet, new Tracking()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return tracking;
    }

    @Override
    public List<Tracking> getTrackingByClientId(User user, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tracking> trackingList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_TRACKING_BY_CLIENT_ID);
            statement.setString(1, String.valueOf(user.getUserId()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackingList.add(createTracking(resultSet, new Tracking()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return trackingList;
    }
    public void setStatusAndStartTime(String id, String status, Long startTime, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        Integer statusId = defineStatus(status);
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_START_TIME_BY_ID);
            statement.setInt(1, statusId);// status_id
            statement.setLong(2, startTime);// time_start
            statement.setString(3, id);// tracking_id
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    Integer defineStatus(String status) {
        Integer statusId = null;
        switch (status) {
            case "NEW_ACTIVITY": {
                statusId = 1;
                break;
            }
            case "IN_PROGRESS": {
                statusId = 2;
                break;
            }
            case "PAUSE": {
                statusId = 3;
                break;
            }
            case "FINISHED": {
                statusId = 4;
                break;
            }
            case "STOP": {
                statusId = 5;
                break;
            }
        }
        return statusId;
    }

    public void setStatusAndTime(String id, String status, String time, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        Integer statusId = defineStatus(status);
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_TIME_BY_ID);
            statement.setInt(1, statusId);// status_id
            statement.setString(2, time);// time
            statement.setString(3, id);// tracking_id
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }
}
