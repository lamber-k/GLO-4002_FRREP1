package core;

import core.request.Request;
import core.request.RequestRepository;

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

    // TODO ALL ne pas save dans repository -> stack
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
        // TODO ALL si on set la limite en dessous du nombre actuel de request rerun ?
    }

    public void addObserverMaximumPendingRequestsReached(MaximumPendingRequestReachedObserver observer) {
        maximumPendingRequestReachedObservers.add(observer);
    }

    // TODO ALL a revoir
    private void notifyMaxPendingRequestReachedObserver() {
        maximumPendingRequestReachedObservers.forEach(MaximumPendingRequestReachedObserver::onMaximumPendingRequestReached);
    }
}
