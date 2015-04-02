package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.Request;

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

    // TODO ALL ne pas save dans repository -> stack
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
        // TODO ALL si on set la limite en dessous du nombre actuel de request rerun ?
    }

    public void cancelPendingRequest(Request request) {
        if (!(pendingRequest.remove(request))) {
            throw new ObjectNotFoundException();
        }
    }

}
