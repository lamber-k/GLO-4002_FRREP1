package ca.ulaval.glo4002.core;

public interface Scheduler extends Runnable {

    boolean isSchedulerRunning();

    void startScheduler();

    void cancelScheduler();

    int getIntervalTimer();

    void setIntervalTimer(int intervalTimer);

    void restartSchedule();

    void runNow();

    @Override
    default void run() {
        runNow();
    }
}
