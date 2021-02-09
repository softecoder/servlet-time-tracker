package commands.implementations.user;

import commands.Command;
import config.ConfigManagerPages;
import constants.Parameters;
import constants.PathPageConstants;
import session.SessionLogic;
import utils.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;

public class BackCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String pageFromRequest = RequestParameterIdentifier.getPageFromRequest(request);
        String page = null;
        if (pageFromRequest != null && pageFromRequest.equals(Parameters.LOGIN)) {
            SessionLogic.sessionIsDead = true;
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
        } else if (pageFromRequest != null && pageFromRequest.equals(Parameters.CLIENT)){
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } else if (pageFromRequest != null && pageFromRequest.equals(Parameters.ADMIN)) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
        }
        return page;
    }
}