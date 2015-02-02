package org.Marv1n.code;

import java.util.concurrent.*;

public class TaskScheduler {
    private static final Integer NOW = 0;
    private TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;
    private Integer intervalTimer;

    public TaskScheduler(ScheduledExecutorService scheduler, Integer intervalTimer, TimeUnit timeUnit) {
        this.scheduler = scheduler;
        this.nextRun = null;
        this.isSchedulerRunning = false;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
    }

    public boolean isSchedulerRunning() {
        return this.isSchedulerRunning;
    }

    public void startScheduler(Runnable task) {
        this.startAtFixedRate(task);
    }

    public void cancelScheduler() {
        if (this.isSchedulerRunning) {
            this.nextRun.cancel(true);
            this.isSchedulerRunning = false;
        }
    }

    public void runNow(Runnable task) {
        this.cancelScheduler();
        task.run();
        this.startAtFixedRate(task);
    }

    private void startAtFixedRate(Runnable task) {
        this.nextRun = this.scheduler.scheduleAtFixedRate(task, this.intervalTimer, this.intervalTimer, this.timeUnit);
        this.isSchedulerRunning = true;
    }

    public Integer getIntervalTimer() {
        return this.intervalTimer;
    }

    public void setIntervalTimer(Integer intervalTimer) {
        this.intervalTimer = intervalTimer;
    }
}
