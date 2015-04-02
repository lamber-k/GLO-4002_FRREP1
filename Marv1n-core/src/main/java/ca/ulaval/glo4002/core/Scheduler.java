package ca.ulaval.glo4002.core;

public abstract class Scheduler implements Runnable {

    public abstract boolean isSchedulerRunning();

    public abstract void startScheduler();

    public abstract void cancelScheduler();

    public abstract int getIntervalTimer();

    public abstract void setIntervalTimer(int intervalTimer);

    public abstract void restartSchedule();

    public abstract void runNow();

    @Override
    public void run() {
        runNow();
    }
}
