package org.Marv1n.code;

import java.util.concurrent.*;

public class TaskScheduler {
    private static final Integer NOW = 0;
    private TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;

    public TaskScheduler(Integer numberOfThread, TimeUnit timeUnit) {
        this.scheduler = Executors.newScheduledThreadPool(numberOfThread);
        this.nextRun = null;
        this.isSchedulerRunning = false;
        this.timeUnit = timeUnit;
    }

    public boolean isSchedulerRunning() {
        return this.isSchedulerRunning;
    }

    public void startScheduler(Integer timer, Runnable task) {
        this.startAtFixedRate(task, timer, timer);
    }

    public void cancelScheduler() {
        if (this.isSchedulerRunning) {
            this.nextRun.cancel(true);
            this.isSchedulerRunning = false;
        }
    }

    public void runNow(Integer timer, Runnable task) throws ExecutionException, InterruptedException {
        this.cancelScheduler();
        this.startAtFixedRate(task, NOW, timer);
    }

    private void startAtFixedRate(Runnable task, Integer start, Integer interval) {
        this.nextRun = this.scheduler.scheduleAtFixedRate(task, start, interval, this.timeUnit);
        this.isSchedulerRunning = true;
    }
}
