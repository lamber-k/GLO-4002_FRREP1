package ca.ulaval.glo4002.core;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskScheduler implements Scheduler {

    private final TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> nextRun;
    private boolean isSchedulerRunning;
    private int intervalTimer;
    private TaskFactory taskFactory;
    private Task task;

    public TaskScheduler(ScheduledExecutorService scheduler, int intervalTimer, TimeUnit timeUnit, TaskFactory taskFactory) {
        this.scheduler = scheduler;
        this.timeUnit = timeUnit;
        this.intervalTimer = intervalTimer;
        this.taskFactory = taskFactory;
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
            nextRun.cancel(false);
            isSchedulerRunning = false;
        }
    }

    @Override
    public int getIntervalTimer() {
        return intervalTimer;
    }

    @Override
    public void setIntervalTimer(int intervalTimer) {
        this.intervalTimer = intervalTimer;
        cancelScheduler();  //TODO ALL Test me properly
        startAtFixedRate();  //TODO ALL Strange behaviour
    }

    @Override
    public void restartSchedule() {
        cancelScheduler();
        startAtFixedRate();
    }

    @Override
    public void runNow() {
        starNowtAtFixedRate();
    }

    @Override
    public void run() {
        startTask();
    }

    private void startAtFixedRate() {
        nextRun = scheduler.scheduleAtFixedRate(this, intervalTimer, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    private void starNowtAtFixedRate() {
        cancelScheduler();
        nextRun = scheduler.scheduleAtFixedRate(this, 0, intervalTimer, timeUnit);
        isSchedulerRunning = true;
    }

    private void startTask() {
        task = taskFactory.createTask();
        task.run();
    }
}
