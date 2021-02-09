package commands.implementations.admin;

import commands.Command;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.Activity;
import entities.Tracking;
import entities.User;
import org.apache.log4j.Logger;
import services.ActivityService;
import services.AdminService;
import services.TrackingService;
import services.UserService;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class CreateActivityCommand implements Command {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private final ActivityService activityService = (ActivityService) ServiceApplicationContext.getInstance().getBean("activityService");
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        Activity activity = AdminService.getInstance().geActivityFromRequest(request);
        try {
            if (AdminService.getInstance().areFieldsFilled(request)) {
                if (activityService.isUniqueActivity(activity)) {
                    activityService.createActivityDB(activity);
                    List<Activity> activityAdminList = activityService.getAllActivities();
                    List<Tracking> trackingList = trackingService.getAllTracking();
                    List<User> userList = userService.getAllUser();
                    userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                    logger.info(MessageConstants.SUCCESS_CREATION);
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.ACTIVITY_EXISTS);
                    List<Activity> activityAdminList = activityService.getAllActivities();
                    List<Tracking> trackingList = trackingService.getAllTracking();
                    List<User> userList = userService.getAllUser();
                    UserService.getInstance().setAttributeToSession(activityAdminList, trackingList, userList, session);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.EMPTY_FIELDS_ACTIVITY);
                List<Activity> activityAdminList = activityService.getAllActivities();
                List<Tracking> trackingList = trackingService.getAllTracking();
                List<User> userList = userService.getAllUser();
                userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
            }
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}