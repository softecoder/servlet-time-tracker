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
import services.TrackingService;
import services.UserService;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class OverviewClientCommand implements Command {
    private static final Logger logger = Logger.getLogger(OverviewClientCommand.class);
    private final ActivityService activityService = (ActivityService) ServiceApplicationContext.getInstance().getBean("activityService");
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String overviewUserId = request.getParameter(Parameters.USER_ID);

        try {
            User overviewUser = userService.getUserById(overviewUserId);
            userService.setAttributeOverviewUserToSession(overviewUser, session);
            List<Tracking> trackingList = trackingService.getAllTracking();
            List<Activity> activityAdminList = activityService.getAllActivities();
            List<User> userList = userService.getAllUser();
            userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH_CLIENT_OVERVIEW);
            logger.info(MessageConstants.SUCCESS_OVERVIEW_CLIENT_COMMAND);
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
