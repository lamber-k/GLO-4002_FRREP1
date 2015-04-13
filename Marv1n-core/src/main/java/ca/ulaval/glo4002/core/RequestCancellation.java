package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;

import java.util.UUID;

public class RequestCancellation {
    private final PendingRequests pendingRequests;
    private final RequestRepository requestRepository;
    private NotificationFactory notificationFactory;

    RequestCancellation(PendingRequests pendingRequests, RequestRepository requestRepository, NotificationFactory notificationFactory) {
        this.pendingRequests = pendingRequests;
        this.requestRepository = requestRepository;
        this.notificationFactory = notificationFactory;
    }

    public void cancelRequestByUUID(UUID id) throws ObjectNotFoundException, InvalidFormatException {
            try {
                pendingRequests.cancelPendingRequest(id, requestRepository, notificationFactory);
            } catch (ObjectNotFoundException e) {
                tryCancelStoredRequest(id);
            }
    }

    private void tryCancelStoredRequest(UUID id) throws ObjectNotFoundException, InvalidFormatException {
        try {
            Request request = requestRepository.findByUUID(id);
            request.cancel();
            requestRepository.persist(request);
            notificationFactory.createNotification(request).announce();
        } catch (RequestNotFoundException e) {
            throw new ObjectNotFoundException();
        }
    }
}
