package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;

import java.util.Optional;
import java.util.UUID;

public class RequestCancellation {
    private final PendingRequests pendingRequests;
    private final RequestRepository requestRepository;

    RequestCancellation(PendingRequests pendingRequests, RequestRepository requestRepository) {
        this.pendingRequests = pendingRequests;
        this.requestRepository = requestRepository;
    }

    public void cancelRequestByUUID(UUID id) throws ObjectNotFoundException, InvalidFormatException {
            try {
                pendingRequests.cancelPendingRequest(id, requestRepository);
            } catch (ObjectNotFoundException e) {
                tryCancelStoredRequest(id);
            }
    }

    private void tryCancelStoredRequest(UUID id) throws ObjectNotFoundException, InvalidFormatException {
        try {
            Request request = requestRepository.findByUUID(id);
            // TODO cancel request
            requestRepository.persist(request);
        } catch (RequestNotFoundException e) {
            throw new ObjectNotFoundException();
        }
    }
}
