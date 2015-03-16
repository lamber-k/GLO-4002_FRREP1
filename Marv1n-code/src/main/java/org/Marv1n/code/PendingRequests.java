package org.Marv1n.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PendingRequests {

    private int maximumPendingRequests;
    private List<Request> pendingRequests = new LinkedList<>();
    private List<MaximumPendingRequestReachedObserver> maximumPendingRequestReachedObservers;

    public PendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.maximumPendingRequestReachedObservers = new ArrayList<>();
    }

    public void addRequest(Request request) {
        pendingRequests.add(request);
        if (pendingRequests.size() >= maximumPendingRequests) {
            notifyMaxPendingRequestReachedObserver();
        }
    }

    public void cancelRequest(Request request) {
        pendingRequests.remove(request);
    }

    public boolean hasPendingRequest() {
        return !pendingRequests.isEmpty();
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }

    public void addObserverMaximumPendingRequestsReached(MaximumPendingRequestReachedObserver observer) {
        maximumPendingRequestReachedObservers.add(observer);
    }

    private void notifyMaxPendingRequestReachedObserver() {
        for (MaximumPendingRequestReachedObserver observer : maximumPendingRequestReachedObservers)
            observer.onMaximumPendingRequestReached();
    }
}
