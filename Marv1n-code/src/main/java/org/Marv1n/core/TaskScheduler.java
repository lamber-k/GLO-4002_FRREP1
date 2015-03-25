package org.Marv1n.core;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskScheduler implements Scheduler {

    private final Runnable task;
    private final TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun = null;
    private boolean isSchedulerRunning = false;
    private int intervalTimer;

    public TaskScheduler(ScheduledExecutorService scheduler, int intervalTimer, TimeUnit timeUnit, Runnable task) {
        this.scheduler = scheduler;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
        this.task = task;
    }

    @Override
    public boolean isSchedulerRunning() {
        return isSchedulerRunning;
    }

    @Override
    public void startScheduler() {
        startAtFixedRate();
    }

    @Override
    public void cancelScheduler() {
        if (isSchedulerRunning) {
            nextRun.cancel(true);
            isSchedulerRunning = false;
        }
    }

    private void startAtFixedRate() {
        nextRun = scheduler.scheduleAtFixedRate(task, intervalTimer, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    @Override
    public int getIntervalTimer() {
        return intervalTimer;
    }

    @Override
    public void setIntervalTimer(int intervalTimer) {
        this.intervalTimer = intervalTimer;
    }

    @Override
    public void restartSchedule() {
        cancelScheduler();
        startAtFixedRate();
    }

    @Override
    public void runNow() {
        cancelScheduler();
        task.run();
        startAtFixedRate();
    }
}
