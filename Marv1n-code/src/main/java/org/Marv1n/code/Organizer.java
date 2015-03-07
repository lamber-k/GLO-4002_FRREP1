package org.Marv1n.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this.pendingRequests.add(request);
        if (this.pendingRequests.size() >= this.maximumPendingRequests) {
            treatPendingRequestsNow();
        }
    }

    public void start() throws SchedulerAlreadyRunningException {
        if (this.taskScheduler.isSchedulerRunning()) {
            throw new SchedulerAlreadyRunningException();
        }
        this.taskScheduler.startScheduler(this.requestTreatment);
    }

    public void stop() {
        if (this.taskScheduler.isSchedulerRunning()) {
            this.taskScheduler.cancelScheduler();
        }
    }

    public boolean hasPendingRequest() {
        return !this.pendingRequests.isEmpty();
    }

    public void treatPendingRequestsNow() {
        this.taskScheduler.runNow(this.requestTreatment);
    }

    public int getMaximumPendingRequests() {
        return this.maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }
}

