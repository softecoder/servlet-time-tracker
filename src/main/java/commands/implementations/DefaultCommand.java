package commands.implementations;

import commands.Command;
import config.ConfigManagerPages;
import constants.PathPageConstants;
import session.SessionLogic;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        SessionLogic.sessionIsDead = true;
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}