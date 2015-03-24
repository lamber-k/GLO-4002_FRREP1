package org.Marv1n.code;

public interface Scheduler {

    public boolean isSchedulerRunning();

    public void startScheduler();

    public void cancelScheduler();

    public int getIntervalTimer();

    public void setIntervalTimer(int intervalTimer);

    public void restartSchedule();

    public void runNow();
}
