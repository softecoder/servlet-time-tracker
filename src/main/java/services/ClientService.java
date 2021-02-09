package services;

import entities.ActivityStatus;
import entities.Tracking;
import entities.User;
import timer.Time;

import java.util.List;

public class ClientService {
    private volatile static ClientService instance;

    private ClientService() {

    }

    public static ClientService getInstance() {
        if (instance == null) {
            synchronized (ClientService.class) {
                if (instance == null) {
                    return instance = new ClientService();
                }
            }
        }
        return instance;
    }

    public Tracking setUpTime(Tracking tracking) {
        Time.getInstance().setStartTime(tracking.getTimeStart());
        Time.getInstance().setDifference(tracking.getDifferenceTime());
        Time.getInstance().stop();
        tracking.setElapsedTime(Time.getInstance().getElapsedTime());
        tracking.setTimeStop(Time.getInstance().getStopTime());
        tracking.setDifferenceTime(Time.getInstance().getDifference());
        return tracking;
    }

    public Tracking setUpDifferenceTime(Tracking tracking) {
        Time.getInstance().setStartTime(tracking.getTimeStart());
        Time.getInstance().setDifference(tracking.getDifferenceTime());
        Time.getInstance().stop();
        tracking.setTimeStop(Time.getInstance().getStopTime());
        tracking.setTimeStart(tracking.getTimeStart()+(Time.getInstance().getDifference()-tracking.getDifferenceTime()));
        tracking.setDifferenceTime(Time.getInstance().getDifference());

        return tracking;
    }

    public boolean ifUserHasNoOpenActivity(User trackingUser, List<Tracking> trackingList) {
        boolean flag = true;
        for (Tracking tracking : trackingList) {
            if (trackingUser.equals(tracking.getUser())) {
                if (tracking.getStatus() == ActivityStatus.IN_PROGRESS) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public Tracking getActiveTracking(List<Tracking> trackingList) {
        Tracking activeTracking = null;
        for (Tracking tracking : trackingList) {
            if (tracking.getStatus() == ActivityStatus.IN_PROGRESS) {
                activeTracking = tracking;
            }
        }
        return activeTracking;
    }
}
