package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.PendingRequests;
import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;

public class StrategyRequestCancellationPending implements IStrategyRequestCancellation {

    private IRequestRepository requestRepository;
    private PendingRequests pendingRequests;

    public StrategyRequestCancellationPending(IRequestRepository requestRepository, PendingRequests pendingRequests) {
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
