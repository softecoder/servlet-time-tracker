package commands.implementations.client;

import commands.Command;
import commands.implementations.admin.BackAdminCommand;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.ActivityStatus;
import entities.Tracking;
import org.apache.log4j.Logger;
import services.ClientService;
import services.TrackingService;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class FinishCommand implements Command {
    private static final Logger logger = Logger.getLogger(BackAdminCommand.class);
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String trackingId = request.getParameter(Parameters.TRACKING_ID);
        try {
            Tracking tracking = trackingService.getTrackingById(trackingId);
            if (tracking.getStatus() == ActivityStatus.IN_PROGRESS) {
                tracking=ClientService.getInstance().setUpTime(tracking);
                tracking.setTimeSwitch(false);
            }
            tracking.setStatus(ActivityStatus.FINISHED);
            trackingService.updateTracking(trackingId, tracking);
            List<Tracking> trackingList = trackingService.getAllTracking();
            trackingService.setAttributeTrackingListToSession(trackingList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
