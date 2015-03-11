package org.Marv1n.code;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskScheduler implements ObserverMaximumPendingRequestReached {

    private final Runnable task;
    private TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;
    private int intervalTimer;

    public TaskScheduler(ScheduledExecutorService scheduler, int intervalTimer, TimeUnit timeUnit, PendingRequestObserver observer, Runnable task) {
        this.scheduler = scheduler;
        this.nextRun = null;
        this.isSchedulerRunning = false;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
        this.task = task;
        observer.register(() -> {this.onMaximumPendingRequestReached();return null;});
    }

    public boolean isSchedulerRunning() {
        return isSchedulerRunning;
    }

    public void startScheduler() {
        startAtFixedRate();
    }

    public void cancelScheduler() {
        if (isSchedulerRunning()) {
            nextRun.cancel(true);
            isSchedulerRunning = false;
        }
    }

    private void startAtFixedRate() {
        nextRun = scheduler.scheduleAtFixedRate(task, intervalTimer, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    public int getIntervalTimer() {
        return intervalTimer;
    }

    public void setIntervalTimer(int intervalTimer) {
        this.intervalTimer = intervalTimer;
    }

    @Override
    public void onMaximumPendingRequestReached() {
        cancelScheduler();
        startAtFixedRate();
    }
}
