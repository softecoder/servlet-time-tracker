package services;

import constants.Parameters;
import entities.Activity;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminService {

    private final static Logger logger = Logger.getLogger(AdminService.class);
    private volatile static AdminService instance;

    private AdminService() {
    }

    public static AdminService getInstance() {
        if (instance == null) {
            synchronized (AdminService.class) {
                if (instance == null) {
                    return instance = new AdminService();
                }
            }
        }
        return instance;
    }

    public Activity geActivityFromRequest(HttpServletRequest request) {
        Activity activity = new Activity();
        String activityName = request.getParameter(Parameters.ACTIVITY_NAME);
        if (activityName != null && !activityName.isEmpty()) {
            activity.setActivityName(activityName);
            logger.info("Success execute retrieving activity from request.");
        }
        return activity;
    }

    public boolean areFieldsFilled(HttpServletRequest request) {
        if (!request.getParameter(Parameters.ACTIVITY_NAME).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}