package org.Marv1n.code;

/**
 * Created by Kevin on 13/03/2015.
 */
public interface Scheduler {
    public boolean isSchedulerRunning();
    public void startScheduler();
    public void cancelScheduler();
    public int getIntervalTimer();
    public void setIntervalTimer(int intervalTimer);
    public void restartSchedule();
}
