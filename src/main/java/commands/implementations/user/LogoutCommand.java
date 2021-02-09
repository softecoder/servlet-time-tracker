package commands.implementations.user;

import commands.Command;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.PathPageConstants;
import org.apache.log4j.Logger;
import session.SessionLogic;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private final static Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        SessionLogic.sessionIsDead = true;
        logger.info(MessageConstants.SUCCESS_LOGOUT);
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}