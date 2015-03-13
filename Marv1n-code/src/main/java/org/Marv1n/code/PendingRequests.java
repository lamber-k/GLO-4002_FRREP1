package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.StrategyRequestCancellation.IStrategyRequestCancellation;
import org.Marv1n.code.StrategyRequestCancellation.StrategyRequestCancellationFactory;

import java.util.*;

public class PendingRequests {
    private int maximumPendingRequests;
    private List<Request> pendingRequests = new LinkedList<>();
    private StrategyRequestCancellationFactory strategyRequestCancellationFactory;
    private List<IObserverMaximumPendingRequestReached> maximumPendingRequestReachedObservers;


    public PendingRequests(int maximumPendingRequests, StrategyRequestCancellationFactory strategyRequestCancellationFactory) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.strategyRequestCancellationFactory = strategyRequestCancellationFactory;
        this.maximumPendingRequestReachedObservers = new ArrayList<>();
    }

    public void addRequest(Request request) {
        pendingRequests.add(request);
        if (pendingRequests.size() >= maximumPendingRequests) {
            notifyMaxPendingRequestReachedObserver();
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional result = pendingRequests.stream().filter((Request r) -> r.getRequestID().equals(requestID)).findFirst();
        if (result.isPresent()) {
            Request request = (Request) result.get();
            IStrategyRequestCancellation strategyRequestCancellation = strategyRequestCancellationFactory.createStrategyCancellation(request.getRequestStatus());
            strategyRequestCancellation.cancelRequest(request);
        }
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

    public void addObserverMaximumPendingRequestsReached(IObserverMaximumPendingRequestReached observer) {
        maximumPendingRequestReachedObservers.add(observer);
    }

    private void notifyMaxPendingRequestReachedObserver() {
        for (IObserverMaximumPendingRequestReached observer : maximumPendingRequestReachedObservers) {
            observer.onMaximumPendingRequestReached();
        }

    }
}
