package org.Marv1n.code;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    private TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;
    private Integer intervalTimer;

    public TaskScheduler(ScheduledExecutorService scheduler, Integer intervalTimer, TimeUnit timeUnit) {
        this.scheduler = scheduler;
        nextRun = null;
        isSchedulerRunning = false;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
    }

    public boolean isSchedulerRunning() {
        return isSchedulerRunning;
    }

    public void startScheduler(Runnable task) {
        startAtFixedRate(task);
    }

    public void cancelScheduler() {
        if (isSchedulerRunning()) {
            nextRun.cancel(true);
            isSchedulerRunning = false;
        }
    }

    public void runNow(Runnable task) {
        cancelScheduler();
        task.run();
        startAtFixedRate(task);
    }

    private void startAtFixedRate(Runnable task) {
        nextRun = scheduler.scheduleAtFixedRate(task, intervalTimer, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    public Integer getIntervalTimer() {
        return intervalTimer;
    }

    public void setIntervalTimer(Integer intervalTimer) {
        this.intervalTimer = intervalTimer;
    }
}
