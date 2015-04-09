package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PendingRequests {

    private int maximumPendingRequests;
    private List<Request> pendingRequest;
    private Scheduler scheduler;

    public PendingRequests(int maximumPendingRequests, TaskSchedulerFactory taskSchedulerFactory) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.pendingRequest = Collections.synchronizedList(new ArrayList<>());
        this.scheduler = taskSchedulerFactory.getTaskScheduler(pendingRequest);
    }

    public void addRequest(Request request) {
        pendingRequest.add(request);
        this.checkLimitIsReached();
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.checkLimitIsReached();
    }

    public void cancelPendingRequest(Request request) {
        if (!(pendingRequest.remove(request))) {
            throw new ObjectNotFoundException();
        }
    }

    private void checkLimitIsReached() {
        if (pendingRequest.size() >= maximumPendingRequests) {
            scheduler.runNow();
        }
    }
}
