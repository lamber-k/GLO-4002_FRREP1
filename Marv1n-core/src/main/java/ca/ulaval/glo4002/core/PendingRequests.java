package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;

import java.util.*;

public class PendingRequests {

    private int maximumPendingRequests;
    private List<Request> pendingRequest;
    private Scheduler scheduler;

    public PendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
        this.pendingRequest = Collections.synchronizedList(new ArrayList<>());
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public List<Request> getCurrentPendingRequest() {
        return cloneCurrentPendingRequests();
    }

    public List<Request> retrieveCurrentPendingRequest() {
        List<Request> requestToGive = pendingRequest;
        pendingRequest = Collections.synchronizedList(new ArrayList<>());
        return requestToGive;
    }

    public void addRequest(Request request) {
        pendingRequest.add(request);
        checkLimitIsReached();
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
        checkLimitIsReached();
    }

    public void cancelPendingRequest(UUID requestId, RequestRepository requestRepository, NotificationFactory notificationFactory) throws ObjectNotFoundException {
        Optional<Request> requestOptional = pendingRequest.stream().filter(r -> r.getRequestID().equals(requestId)).findFirst();
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            request.cancel();
            requestRepository.persist(request);
            pendingRequest.remove(request);
            notificationFactory.createNotification(request).announce();
        } else {
            throw new ObjectNotFoundException();
        }
    }

    private void checkLimitIsReached() {
        if (pendingRequest.size() >= maximumPendingRequests) {
            scheduler.runNow();
        }
    }

    private List<Request> cloneCurrentPendingRequests() {
        List<Request> clone = new ArrayList<>();
        clone.addAll(pendingRequest);
        return clone;
    }
}
