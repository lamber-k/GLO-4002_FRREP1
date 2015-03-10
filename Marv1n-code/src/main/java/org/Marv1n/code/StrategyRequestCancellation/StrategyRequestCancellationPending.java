package org.Marv1n.code.StrategyRequestCancellation;

import org.Marv1n.code.Repository.Request.IRequestRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;

/**
 * Created by mathieu on 3/9/15.
 */
public class StrategyRequestCancellationPending implements IStrategyRequestCancellation {

    private IRequestRepository requestRepository;

    public StrategyRequestCancellationPending(IRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public void cancelRequest(Request request) {
        requestRepository.remove(request);
        request.setRequestStatus(RequestStatus.CANCELED);
        requestRepository.create(request);
        //TODO use update in repository
    }
}
