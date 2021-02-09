package entities;

import java.io.Serializable;
import java.util.Objects;

public class Tracking implements Serializable {
    private Integer trackingId;
    private User user;
    private Activity activity;
    private ActivityStatus status;
    private UserRequest userRequest;
    private String elapsedTime;
    private Long timeStart;
    private Long timeStop;
    private Long differenceTime;
    private boolean timeSwitch;


    public Tracking() {
    }

    public Tracking(User user, Activity activity, ActivityStatus status, UserRequest userRequest, String elapsedTime,
                    Long timeStart, Long timeStop, Long differenceTime, boolean timeSwitch) {
        this.user = user;
        this.activity = activity;
        this.status = status;
        this.userRequest = userRequest;
        this.elapsedTime = elapsedTime;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.differenceTime = differenceTime;
        this.timeSwitch = timeSwitch;
    }

    public Integer getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Long timeStop) {
        this.timeStop = timeStop;
    }

    public Long getDifferenceTime() {
        return differenceTime;
    }

    public void setDifferenceTime(Long differenceTime) {
        this.differenceTime = differenceTime;
    }

    public boolean isTimeSwitch() {
        return timeSwitch;
    }

    public void setTimeSwitch(boolean timeSwitch) {
        this.timeSwitch = timeSwitch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tracking tracking = (Tracking) o;

        if (timeSwitch != tracking.timeSwitch) return false;
        if (!Objects.equals(trackingId, tracking.trackingId)) return false;
        if (!Objects.equals(user, tracking.user)) return false;
        if (!Objects.equals(activity, tracking.activity)) return false;
        if (status != tracking.status) return false;
        if (userRequest != tracking.userRequest) return false;
        if (!Objects.equals(elapsedTime, tracking.elapsedTime))
            return false;
        if (!Objects.equals(timeStart, tracking.timeStart)) return false;
        if (!Objects.equals(timeStop, tracking.timeStop)) return false;
        return Objects.equals(differenceTime, tracking.differenceTime);
    }

    @Override
    public int hashCode() {
        int result = trackingId != null ? trackingId.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (userRequest != null ? userRequest.hashCode() : 0);
        result = 31 * result + (elapsedTime != null ? elapsedTime.hashCode() : 0);
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeStop != null ? timeStop.hashCode() : 0);
        result = 31 * result + (differenceTime != null ? differenceTime.hashCode() : 0);
        result = 31 * result + (timeSwitch ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tracking{" +
                "trackingId=" + trackingId +
                ", user=" + user +
                ", activity=" + activity +
                ", status=" + status +
                ", userRequest=" + userRequest +
                ", elapsedTime='" + elapsedTime + '\'' +
                ", timeStart=" + timeStart +
                ", timeStop=" + timeStop +
                ", differenceTime=" + differenceTime +
                ", timeSwitch=" + timeSwitch +
                '}';
    }
}
