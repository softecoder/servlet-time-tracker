package services;

import connection.ConnectionPool;
import connection.TransactionHandler;
import constants.Parameters;
import dao.interfacesdao.TrackingDAO;
import entities.Tracking;
import entities.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class TrackingService {
    private volatile static TrackingService instance;
    private TrackingDAO trackingDao;
    private ConnectionPool connectionPool;

    private TrackingService() {
    }

    public static TrackingService getInstance() {
        if (instance == null) {
            synchronized (TrackingService.class) {
                if (instance == null) {
                    return instance = new TrackingService();
                }
            }
        }
        return instance;
    }

    public void setTrackingDao(TrackingDAO trackingDao) {
        this.trackingDao = trackingDao;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    public void registerTracking(Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.add(tracking, connection),connectionPool.getConnection()
        );
    }
    public List<Tracking> getTrackingByClientId(User user) throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                        trackingList[0] = trackingDao.getTrackingByClientId(user, connection),
                connectionPool.getConnection()
        );
        return trackingList[0];
    }

    public Tracking getTrackingById(String trackingId) throws SQLException {
        final Tracking[] tracking = new Tracking[1];
        TransactionHandler.runInTransaction(connection ->
                        tracking[0] = trackingDao.getTrackingById(trackingId, connection),
                connectionPool.getConnection()
        );
        return tracking[0];
    }
    public void deleteTrackingById(Integer trackingId) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.deleteTrackingById(trackingId, connection),connectionPool.getConnection()
        );
    }
    public List<Tracking> getAllTracking() throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                        trackingList[0] = trackingDao.getAll(connection),connectionPool.getConnection()
        );
        return trackingList[0];
    }

    public void setAttributeTrackingListToSession(List<Tracking> trackingList, HttpSession session) {
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
    }
    public void updateTracking(String id, Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.updateTrackingById(id, tracking, connection),connectionPool.getConnection()
        );
    }
    public void setStatusAndTimeStartTracking(String id, String status, Long startTime) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                trackingDao.setStatusAndStartTime(id, status, startTime, connection),connectionPool.getConnection()
        );
    }
}
