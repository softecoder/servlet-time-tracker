package commands.implementations.client;

import commands.Command;
import commands.implementations.admin.BackAdminCommand;
import config.ConfigManagerPages;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.ActivityStatus;
import entities.Tracking;
import entities.User;
import org.apache.log4j.Logger;
import services.ClientService;
import services.TrackingService;
import timer.Time;
import utils.ServiceApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class StartTimeCommand implements Command {
    private static final Logger logger = Logger.getLogger(BackAdminCommand.class);
    private final TrackingService trackingService = (TrackingService) ServiceApplicationContext.getInstance().getBean("trackingService");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String trackingId = request.getParameter(Parameters.TRACKING_ID);
        try {
            Tracking tracking = trackingService.getTrackingById(trackingId);
            User trackingUser = tracking.getUser();
            List<Tracking> trackingList = trackingService.getAllTracking();
            if (ClientService.getInstance().ifUserHasNoOpenActivity(trackingUser, trackingList)) {
                Time.getInstance().start();
                tracking = trackingService.getTrackingById(trackingId);
                tracking.setTimeSwitch(true);
                trackingService.updateTracking(trackingId, tracking);
                trackingService.setStatusAndTimeStartTracking(trackingId,
                        ActivityStatus.IN_PROGRESS.toString(), Time.getInstance().getStartTime());
                trackingList = trackingService.getAllTracking();
                trackingService.setAttributeTrackingListToSession(trackingList, session);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            } else {
                Tracking activeTracking = ClientService.getInstance().getActiveTracking(trackingList);
                activeTracking = ClientService.getInstance().setUpDifferenceTime(activeTracking);
                trackingService.updateTracking(activeTracking.getTrackingId().toString(), activeTracking);
                trackingList = trackingService.getAllTracking();
                trackingService.setAttributeTrackingListToSession(trackingList, session);
                request.setAttribute("duplicateStart", "true");
                request.setAttribute("trackingId", trackingId);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            }
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
