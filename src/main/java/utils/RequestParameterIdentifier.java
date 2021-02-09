package utils;

import constants.Parameters;
import entities.User;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterIdentifier {

    public static User getUserLoginPasswordFromRequest(HttpServletRequest request) {
        User user = new User();
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        if (login != null && !login.isEmpty()) {
            user.setLogin(login);
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        return user;
    }

    public static User getUserFromRequest(HttpServletRequest request) {
        User user = new User();
        String firstName = request.getParameter(Parameters.FIRST_NAME);
        String surName = request.getParameter(Parameters.SURNAME);
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        if ((firstName != null && !firstName.isEmpty())
                & (surName != null && !surName.isEmpty())
                & (login != null && !login.isEmpty())
                & (password != null && !password.isEmpty())) {
            user.setFirstName(firstName);
            user.setSurName(surName);
            user.setLogin(login);
            user.setPassword(password);
            user.setRequestAdd(false);
        }
        return user;
    }

    public static String getPageFromRequest(HttpServletRequest request) {
        String pageFromRequest = request.getSession().getAttribute(Parameters.BACK_PAGE).toString();
        request.getSession().removeAttribute(Parameters.BACK_PAGE);
        if (pageFromRequest != null && !pageFromRequest.isEmpty()) {
            return pageFromRequest;
        } else {
            return null;
        }
    }

    public static boolean areFieldsFilled(HttpServletRequest request) {
        return !request.getParameter(Parameters.LOGIN).isEmpty()
                && !request.getParameter(Parameters.PASSWORD).isEmpty()
                && !request.getParameter(Parameters.FIRST_NAME).isEmpty()
                && !request.getParameter(Parameters.SURNAME).isEmpty();
    }
}