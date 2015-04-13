package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;

import java.util.*;

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

    public void cancelPendingRequest(UUID requestId, RequestRepository requestRepository, NotificationFactory notificationFactory) throws ObjectNotFoundException, InvalidFormatException {
        Optional<Request> requestOptional = this.pendingRequest.stream().filter(r -> r.getRequestID().equals(requestId)).findFirst();
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            request.cancel();
            requestRepository.persist(request);
            this.pendingRequest.remove(request);
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
}
