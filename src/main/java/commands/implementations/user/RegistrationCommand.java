package commands.implementations.user;

import commands.Command;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.User;
import org.apache.log4j.Logger;
import services.UserService;
import session.SessionLogic;
import utils.RequestParameterIdentifier;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");

    @Override
    public String execute(HttpServletRequest request) {
        String page ;
        User user = RequestParameterIdentifier.getUserFromRequest(request);
        try {
            if (RequestParameterIdentifier.areFieldsFilled(request)) {
                if (userService.isUniqueUser(user)) {
                    userService.registerUser(user);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.SUCCESS_REGISTRATION);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
                    logger.info(MessageConstants.SUCCESS_REGISTRATION);
                    SessionLogic.sessionIsDead = true;
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.USER_EXISTS);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.EMPTY_FIELDS);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
            }
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}