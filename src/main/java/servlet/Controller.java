package servlet;

import commands.Command;
import commands.factory.CommandsFactory;
import config.ConfigManagerPages;
import constants.PathPageConstants;
import session.SessionLogic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {

    public Controller() {
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = SessionLogic.getSession(request);
        if (SessionLogic.isSessionNotAlive(session)) {
            String page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.SESSION_PAGE_PATH);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            CommandsFactory factory = CommandsFactory.getInstance();
            Command command = factory.defineCommandFrom(request);
            String page = command.execute(request);
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
                response.sendRedirect(request.getContextPath() + page);
                SessionLogic.sessionIsDead = true;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}