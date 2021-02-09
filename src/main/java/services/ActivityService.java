package services;

import connection.ConnectionPool;
import connection.TransactionHandler;
import dao.interfacesdao.ActivityDAO;
import entities.Activity;

import java.sql.SQLException;
import java.util.List;

public class ActivityService {

    private volatile static ActivityService instance;
    private ActivityDAO activityDao;
    private ConnectionPool connectionPool;

    private ActivityService() {
    }

    public static ActivityService getInstance() {
        if (instance == null) {
            synchronized (ActivityService.class) {
                if (instance == null) {
                    return instance = new ActivityService();
                }
            }
        }
        return instance;
    }

    public void setActivityDao(ActivityDAO activityDao) {
        this.activityDao = activityDao;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void createActivityDB(Activity activity) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                activityDao.add(activity, connection),connectionPool.getConnection()
        );
    }

    public Activity getActivityById(String id) throws SQLException {
        final Activity[] activity = new Activity[1];
        TransactionHandler.runInTransaction(connection ->
                activity[0] = activityDao.getById(id, connection),connectionPool.getConnection()
        );
        return activity[0];
    }

    public void updateActivity(Activity activity) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                activityDao.update(activity, connection),connectionPool.getConnection()
        );
    }

    public List<Activity> getAllActivities() throws SQLException {
        final List<Activity>[] activityList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                activityList[0] = activityDao.getAll(connection),connectionPool.getConnection()
        );
        return activityList[0];
    }

    public boolean isUniqueActivity(Activity activity) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = activityDao.checkUniqueActivity(activity.getActivityName(), connection),
                connectionPool.getConnection()
        );
        return isUnique[0];
    }

    public boolean isUniqueClientActivity(String id, String userId) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = activityDao.checkUniqueActivityByUser(id, userId, connection),
                connectionPool.getConnection()
        );
        return isUnique[0];
    }
}