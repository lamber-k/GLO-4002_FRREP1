package ca.ulaval.glo4002.core.request;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Request {

    @Id
    private final UUID requestID;
    private final int numberOfSeatsNeeded;
    private final int priority;
    private final UUID responsibleUUID;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    public Request(int numberOfSeatsNeeded, int priority, UUID responsibleUUID) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsibleUUID = responsibleUUID;
    }

    public Request() {
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = 0;
        this.priority = 0;
        this.responsibleUUID = null;
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
        return rhs != null && rhs instanceof Request && hashCode() == rhs.hashCode();
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

    public void cancel() {
        status = RequestStatus.CANCELED;
    }
}