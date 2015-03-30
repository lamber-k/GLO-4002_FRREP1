package org.Marv1n.core;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestRepository;

import java.util.ArrayList;
import java.util.List;

public class PendingRequests {

    private int maximumPendingRequests;
    private List<MaximumPendingRequestReachedObserver> maximumPendingRequestReachedObservers;
    private RequestRepository requestRepository;

    public PendingRequests(int maximumPendingRequests, RequestRepository requestRepository) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.maximumPendingRequestReachedObservers = new ArrayList<>();
        this.requestRepository = requestRepository;
    }

    public void addRequest(Request request) {
        requestRepository.persist(request);
        if (requestRepository.findAllPendingRequest().size() >= maximumPendingRequests) {
            notifyMaxPendingRequestReachedObserver();
        }
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
        maximumPendingRequestReachedObservers.forEach(MaximumPendingRequestReachedObserver::onMaximumPendingRequestReached);
    }
}
