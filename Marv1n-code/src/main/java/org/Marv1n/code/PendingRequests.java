package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.StrategyRequestCancellation.IStrategyRequestCancellation;
import org.Marv1n.code.StrategyRequestCancellation.StrategyRequestCancellationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PendingRequests {
    private int maximumPendingRequests;
    private IRequestRepository requestRepository;
    private StrategyRequestCancellationFactory strategyRequestCancellationFactory;
    private List<ObserverMaximumPendingRequestReached> maximumPendingRequestReachedsObservers;


    public PendingRequests(int maximumPendingRequests, IRequestRepository requestRepository, StrategyRequestCancellationFactory strategyRequestCancellationFactory) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.requestRepository = requestRepository;
        this.strategyRequestCancellationFactory = strategyRequestCancellationFactory;
        this.maximumPendingRequestReachedsObservers = new ArrayList<>();
    }

    public void addRequest(Request request) {
        requestRepository.create(request);
        if (requestRepository.findAllPendingRequest().size() >= maximumPendingRequests) {
            notifyMaxPendingRequestReachedObserver();
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional result = requestRepository.findByUUID(requestID);
        if (result.isPresent()) {
            Request request = (Request) result.get();
            IStrategyRequestCancellation strategyRequestCancellation = strategyRequestCancellationFactory.createStrategyCancellation(request.getRequestStatus());
            strategyRequestCancellation.cancelRequest(request);
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

    public void addObserverMaximumPendingRequestsReached(ObserverMaximumPendingRequestReached observer) {
        maximumPendingRequestReachedsObservers.add(observer);
    }

    private void notifyMaxPendingRequestReachedObserver() {
        for (ObserverMaximumPendingRequestReached observer : maximumPendingRequestReachedsObservers) {
            observer.onMaximumPendingRequestReached();
        }

    }
}
