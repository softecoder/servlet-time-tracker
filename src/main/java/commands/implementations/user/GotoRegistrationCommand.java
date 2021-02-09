package commands.implementations.user;

import commands.Command;
import config.ConfigManagerPages;
import constants.PathPageConstants;

import javax.servlet.http.HttpServletRequest;

public class GotoRegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
    }
}