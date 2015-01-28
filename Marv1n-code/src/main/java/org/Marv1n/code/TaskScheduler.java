package org.Marv1n.code;

import java.util.concurrent.*;

/**
 * Created by Rafaël on 27/01/2015.
 */
public class TaskScheduler {
    private static final Integer NOW = 0;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;

    public TaskScheduler(Integer numberOfThread) {
        this.scheduler = Executors.newScheduledThreadPool(numberOfThread);
        this.nextRun = null;
        this.isSchedulerRunning = false;
    }

    public boolean IsSchedulerRunning() {
        return this.isSchedulerRunning;
    }

    public void startScheduler(Integer timer, Runnable task) {
        this.nextRun = this.scheduler.scheduleAtFixedRate(task, timer, timer, TimeUnit.SECONDS);
        this.isSchedulerRunning = true;
    }


    public void cancelScheduler() {
        if (this.isSchedulerRunning) {
            this.nextRun.cancel(true);
            this.isSchedulerRunning = false;
        }
    }

    public void runOnce(Runnable runnable) throws ExecutionException, InterruptedException {
        ScheduledFuture<?> newTask = this.scheduler.schedule(runnable, NOW, TimeUnit.SECONDS);
        newTask.get();
    }
}
