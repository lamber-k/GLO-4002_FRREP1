package core;

import core.request.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PendingRequests {

    private int maximumPendingRequests;
    private List<Request> pendingRequest;
    private Scheduler scheduler;

    public PendingRequests(int maximumPendingRequests, TaskSchedulerFactory shedulerFactory) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.pendingRequest = Collections.synchronizedList(new ArrayList<>());
        this.scheduler = shedulerFactory.getTaskSheduler(pendingRequest);
    }

    public void addRequest(Request request) {
        pendingRequest.add(request);
        if (pendingRequest.size() >= maximumPendingRequests) {
            scheduler.runNow();
        }
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }

}
