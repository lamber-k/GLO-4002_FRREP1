package org.Marv1n.code;

import java.util.UUID;

public class Request {

    private final UUID requestID;
    private int numberOfSeatsNeeded;
    private int priority;
    private RequestStatus status;
    private UUID responsibleUUID;

    public Request(int numberOfSeatsNeeded, int priority, UUID responsibleUUID) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsibleUUID = responsibleUUID;
    }

    public Request(int numberOfSeatsNeeded, int priority, UUID responsibleUUID, RequestStatus state) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = state;
        this.responsibleUUID = responsibleUUID;
    }

    public RequestStatus getRequestStatus() {
        return status;
    }

    public void setRequestStatus(RequestStatus status) {
        this.status = status;
    }

    public int getNumberOfSeatsNeeded() {
        return numberOfSeatsNeeded;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Request) {
            return hashCode() == rhs.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return requestID.hashCode();
    }

    public UUID getRequestID() {
        return requestID;
    }

    public UUID getResponsibleUUID() {
        return responsibleUUID;
    }
}