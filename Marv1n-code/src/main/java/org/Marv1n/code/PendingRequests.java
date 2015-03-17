package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.RequestRepository;

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
        requestRepository.create(request);
        if (requestRepository.findAllPendingRequest().size() >= maximumPendingRequests) {
            notifyMaxPendingRequestReachedObserver();
        }
    }


    public boolean hasPendingRequest() {
        return !requestRepository.findAllPendingRequest().isEmpty();
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
