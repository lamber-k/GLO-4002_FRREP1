package org.Marv1n.code;

import org.Marv1n.code.Repository.Request.IRequestRepository;

import java.util.Optional;
import java.util.UUID;

public class PendingRequests {
    private Organizer organizer;
    private int maximumPendingRequests;
    private IRequestRepository requests;

    public PendingRequests(Organizer organizer, int maximumPendingRequests, IRequestRepository requestRepository) {
        this.organizer = organizer;
        this.maximumPendingRequests = maximumPendingRequests;
        this.requests = requestRepository;
    }

    public void addRequest(Request request) {
        requests.create(request);
        if (requests.findAllPendingRequest().size() >= maximumPendingRequests) {
            organizer.treatPendingRequestsNow();
        }
    }

    public void cancelRequest(UUID requestID) {
        Optional result = requests.findByUUID(requestID);
        if (result.isPresent()) {
            Request request = (Request) result.get();
            //TODO update within repository removing and recreating isn't the best way to do so
            if (request.getRequestStatus().equals(RequestStatus.PENDING) || request.getRequestStatus().equals(RequestStatus.ACCEPTED)) {
                //TODO notify cancellation
                requests.remove(request);
                request.setRequestStatus(RequestStatus.CANCELED);
                requests.create(request);
            }
        }
    }

    public boolean hasPendingRequest() {
        return !requests.findAllPendingRequest().isEmpty();
    }

    public int getMaximumPendingRequests() {
        return maximumPendingRequests;
    }

    public void setMaximumPendingRequests(int maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }
}
