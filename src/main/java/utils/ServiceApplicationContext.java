package utils;

import connection.ConnectionPool;
import dao.daoimpl.ActivityDAOImpl;
import dao.daoimpl.TrackingDAOImpl;
import dao.daoimpl.UserDAOImpl;
import services.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceApplicationContext {
    private volatile static ServiceApplicationContext instance;
    private final Map<String, Object> beanContainer = new HashMap<>();

    private ServiceApplicationContext() {
        beanContainer.put("activityService", ActivityService.getInstance());
        beanContainer.put("adminService", AdminService.getInstance());
        beanContainer.put("clientService", ClientService.getInstance());
        beanContainer.put("trackingService", TrackingService.getInstance());
        beanContainer.put("userService", UserService.getInstance());
    }

    public static ServiceApplicationContext getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    return instance = new ServiceApplicationContext();
                }
            }
        }
        return instance;
    }

    public Object getBean(String serviceName) {
        injectDependencies(serviceName);
        return beanContainer.get(serviceName);
    }

    private void injectDependencies(String serviceName) {
        switch (serviceName) {
            case "activityService": {
                ActivityService activityService = ActivityService.getInstance();
                // inject dependency in activityService
                activityService.setActivityDao(ActivityDAOImpl.getInstance());
                activityService.setConnectionPool(ConnectionPool.getInstance());
                break;
            }
            case "trackingService": {
                TrackingService trackingService = TrackingService.getInstance();
                trackingService.setTrackingDao(TrackingDAOImpl.getInstance());
                trackingService.setConnectionPool(ConnectionPool.getInstance());
                break;
            }
            case "userService": {
                UserService userService = UserService.getInstance();
                userService.setUserDao(UserDAOImpl.getInstance());
                userService.setConnectionPool(ConnectionPool.getInstance());
                break;
            }
        }
    }
}
