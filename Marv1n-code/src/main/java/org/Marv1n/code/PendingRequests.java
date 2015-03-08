package org.Marv1n.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PendingRequests {
    private List<Request> pendingRequests = Collections.synchronizedList(new ArrayList<>());
    private Organizer organizer;
    private int maximumPendingRequests;

    public PendingRequests(Organizer organizer, int maximumPendingRequests) {
        this.organizer = organizer;
        this.maximumPendingRequests = maximumPendingRequests;
    }

    public void addRequest(Request request) {
        pendingRequests.add(request);
        if (pendingRequests.size() >= getMaximumPendingRequests()) {
            organizer.treatPendingRequestsNow();
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
}
