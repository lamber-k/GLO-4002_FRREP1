package ca.ulaval.glo4002.core;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskScheduler extends Scheduler {

    private final TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun = null;
    private boolean isSchedulerRunning = false;
    private int intervalTimer;
    private TaskFactory taskFactory;
    private Task task;

    public TaskScheduler(ScheduledExecutorService scheduler, int intervalTimer, TimeUnit timeUnit, TaskFactory taskFactory) {
        this.scheduler = scheduler;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
        this.taskFactory = taskFactory;
        this.task = null;
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
        nextRun = scheduler.scheduleAtFixedRate(this, intervalTimer, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    @Override
    public int getIntervalTimer() {
        return intervalTimer;
    }

    @Override
    public void setIntervalTimer(int intervalTimer) {
        this.intervalTimer = intervalTimer;
        cancelScheduler();
        startAtFixedRate();
    }

    @Override
    public void restartSchedule() {
        cancelScheduler();
        startAtFixedRate();
    }

    @Override
    public void runNow() {
        cancelScheduler();
        Task previousTask = task;
        task = taskFactory.createTask(previousTask);
        task.start();
        startAtFixedRate();
    }
}
