package commands.implementations.user;

import commands.Command;
import commands.implementations.admin.PaginationCommand;
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
import utils.RequestParameterIdentifier;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class LoginCommand implements Command {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);
    private final ActivityService activityService = (ActivityService) ServiceApplicationContext.getInstance().getBean("activityService");
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");


    @Override
    public String execute(HttpServletRequest request) {
        String page ;
        User user = RequestParameterIdentifier.getUserLoginPasswordFromRequest(request);
        HttpSession session = request.getSession(false);
        try {
            if (userService.checkUserAuthorization(user.getLogin(), user.getPassword())) {
                List<Activity> activityAdminList = activityService.getAllActivities();
                List<Tracking> trackingList = trackingService.getAllTracking();
                List<User> userList = userService.getAllUser();
                int itemsPerPage = PaginationCommand.itemsPerPage;
                List<String> numbersPages = userService.getNumbersPages(userList, itemsPerPage);
                String lastPage = String.valueOf(numbersPages.size());
                String currentPage = "1";
                userService.setPaginationAttributeToSession(numbersPages, lastPage, currentPage,
                        itemsPerPage, session);
                user = userService.getUserByLogin(user.getLogin());
                userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                switch (user.getUserType().getUserType()) {
                    case "admin":
                        User adminUser = user;
                        userService.setAttributeAdminToSession(adminUser, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                        break;
                    case "client":
                        User clientUser = user;
                        userService.setAttributeClientToSession(clientUser, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                    default:
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                }
                logger.info(MessageConstants.SUCCESS_LOGIN);
            } else {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_PASSWORD, MessageConstants.WRONG_LOGIN_OR_PASSWORD);
            }
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}