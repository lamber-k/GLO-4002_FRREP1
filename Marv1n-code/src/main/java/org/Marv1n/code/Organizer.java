package org.Marv1n.code;

public class Organizer {

    private TaskScheduler taskScheduler;
    private RequestTreatment requestTreatment;

    public Organizer(TaskScheduler taskScheduler, RequestTreatment requestTreatment) {
        this.taskScheduler = taskScheduler;
        this.requestTreatment = requestTreatment;
    }

    public void start() throws SchedulerAlreadyRunningException {
        if (taskScheduler.isSchedulerRunning()) {
            throw new SchedulerAlreadyRunningException();
        }
        taskScheduler.startScheduler(requestTreatment);
    }

    public void stop() {
        if (taskScheduler.isSchedulerRunning()) {
            taskScheduler.cancelScheduler();
        }
    }

    public void treatPendingRequestsNow() {
        taskScheduler.runNow(requestTreatment);
    }
}

