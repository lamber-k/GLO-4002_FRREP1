package org.Marv1n.code;

import java.util.*;

public class Organizer {

    private List<Request> pendingRequests;
    private TaskScheduler taskScheduler;
    private int maximumPendingRequests;
    private RequestTreatment requestTreatment;

    public Organizer(TaskScheduler scheduler, Integer maximumPendingRequests, RequestTreatment requestTreatment) {
        this.pendingRequests = Collections.synchronizedList(new ArrayList<>());
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.requestTreatment = requestTreatment;
    }

    public void addRequest(Request request) {
        pendingRequests.add(request);
        if (pendingRequests.size() >= maximumPendingRequests) {
            treatPendingRequestsNow();
        }
    }

    public void start() throws SchedulerAlreadyRunningException {
        if (taskScheduler.isSchedulerRunning()) {
            throw new SchedulerAlreadyRunningException();
        }
        taskScheduler.startScheduler(requestTreatment);
    }

    public void stop()
    {
        if (taskScheduler.isSchedulerRunning()) {
            taskScheduler.cancelScheduler();
        }
    }

    public boolean hasPendingRequest() {
        return !pendingRequests.isEmpty();
    }

    public void treatPendingRequestsNow() {
        taskScheduler.runNow(requestTreatment);
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }
}

