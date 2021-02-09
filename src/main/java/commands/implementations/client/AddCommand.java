package commands.implementations.client;

import commands.Command;
import commands.implementations.admin.CreateActivityCommand;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
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


public class AddCommand implements Command {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private final ActivityService activityService = (ActivityService) ServiceApplicationContext.getInstance().getBean("activityService");
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String userId = request.getParameter(Parameters.USER_ID);
        try {
            User clientUser = userService.getUserById(userId);
            clientUser.setRequestAdd(true);
            userService.updateUser(clientUser);
            List<User> userList = userService.getAllUser();
            List<Tracking> trackingList = trackingService.getAllTracking();
            userService.setAttributeClientToSession(clientUser, session);
            userService.setAttributeToSession(trackingList, userList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            logger.info(MessageConstants.SUCCESS_ADD_REQUEST);
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
