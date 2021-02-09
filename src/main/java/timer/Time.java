package timer;

import java.util.concurrent.TimeUnit;

public class Time {
    private volatile static Time instance;

    private Time() {
    }

    public static Time getInstance() {
        if (instance == null) {
            synchronized (Time.class) {
                if (instance == null) {
                    instance = new Time();
                }
            }
        }
        return instance;
    }

    private String elapsedTime;

    private long difference = 0;

    private long startTime = 0;

    private long stopTime = 0;

    public final void start() {
        startTime = System.currentTimeMillis();
    }

    public final void stop() {
        stopTime = System.currentTimeMillis() + difference;
        elapsedTime();
    }

    public void elapsedTime() {
        difference = (stopTime - startTime); // in ms
        elapsedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(difference),
                TimeUnit.MILLISECONDS.toMinutes(difference) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                TimeUnit.MILLISECONDS.toSeconds(difference) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Long getDifference() {
        return difference;
    }

    public void setDifference(Long difference) {
        this.difference = difference;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }
}
