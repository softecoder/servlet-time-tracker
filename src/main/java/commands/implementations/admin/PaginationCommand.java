package commands.implementations.admin;

import commands.Command;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.User;
import org.apache.log4j.Logger;
import services.UserService;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class PaginationCommand implements Command {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private final UserService userService = (UserService) ServiceApplicationContext.getInstance().getBean("userService");

    public static int itemsPerPage =4;
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        try {
            List<User> userList = userService.getAllUser();
            List<String> numbersPages = userService.getNumbersPages(userList, itemsPerPage);
            String lasPage = String.valueOf(numbersPages.size());
            String currentPage = request.getParameter("currentPage");
            userService.setPaginationAttributeToSession(numbersPages, lasPage, currentPage,itemsPerPage,
                    session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
            logger.info(MessageConstants.SUCCESS_CHOOSING_PAGE);
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
