package org.Marv1n.code.RequestCancellationStrategy;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;

public class RequestCancellationPendingStrategy implements RequestCancellationStrategy {

    private RequestRepository requestRepository;
    private PendingRequests pendingRequests;

    public RequestCancellationPendingStrategy(RequestRepository requestRepository, PendingRequests pendingRequests) {
        this.requestRepository = requestRepository;
        this.pendingRequests = pendingRequests;
    }

    @Override
    public void cancelRequest(Request request) {
        requestRepository.remove(request);
        request.setRequestStatus(RequestStatus.CANCELED);
        requestRepository.create(request);
        pendingRequests.cancelRequest(request);
    }
}
