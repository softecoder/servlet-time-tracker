package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity implements Serializable {
    public static List<String> activityNameList = new ArrayList<>();
    private Integer activityId;
    private String activityName;

    public Activity() {
    }

    public Activity(Integer activityId, String activityName) {
        this.activityId = activityId;
        this.activityName = activityName;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<String> getActivityList() {
        return activityNameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (!Objects.equals(activityId, activity.activityId)) return false;
        return Objects.equals(activityName, activity.activityName);
    }

    @Override
    public int hashCode() {
        int result = activityId != null ? activityId.hashCode() : 0;
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                '}';
    }

}
