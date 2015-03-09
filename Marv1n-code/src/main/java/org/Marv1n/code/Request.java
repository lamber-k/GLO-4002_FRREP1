package org.Marv1n.code;

import java.util.UUID;

public class Request {

    private UUID requestID;
    private int numberOfSeatsNeeded;
    private int priority;
    private RequestStatus status;

    public Request(int numberOfSeatsNeeded, int priority) {
        this.priority = priority;
        requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
    }

    public Request(int numberOfSeatsNeeded, int priority, RequestStatus state) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = state;
    }

    public RequestStatus getRequestStatus() {
        return this.status;
    }

    public void setRequestStatus(RequestStatus state) {
        this.status = state;
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
        return (false);
    }

    @Override
    public int hashCode() {
        return (requestID.hashCode());
    }


    public UUID getRequestID() {
        return requestID;
    }
}
