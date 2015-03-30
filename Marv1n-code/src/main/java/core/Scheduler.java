package core;

public interface Scheduler {

    boolean isSchedulerRunning();

    void startScheduler();

    void cancelScheduler();

    int getIntervalTimer();

    void setIntervalTimer(int intervalTimer);

    void restartSchedule();

    void runNow();
}
