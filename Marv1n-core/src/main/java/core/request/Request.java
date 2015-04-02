package core.request;

import java.util.UUID;

public class Request {

    private final UUID requestID;
    private final int numberOfSeatsNeeded;
    private final int priority;
    private final UUID responsibleUUID;
    private RequestStatus status;

    public Request(int numberOfSeatsNeeded, int priority, UUID responsibleUUID) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsibleUUID = responsibleUUID;
    }

    public RequestStatus getRequestStatus() {
        return status;
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
        } else {
            return false;
        }
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

    // TODO ALL notifier
    public void accept() {
        status = RequestStatus.ACCEPTED;
    }

    public void refuse() {
        status = RequestStatus.REFUSED;
    }
}