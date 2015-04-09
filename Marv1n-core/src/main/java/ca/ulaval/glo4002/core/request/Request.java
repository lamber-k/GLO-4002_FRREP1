package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.person.Person;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Request {

    @Id
    private final UUID requestID;
    private final int numberOfSeatsNeeded;
    private final int priority;
    // TODO Kevin faire que ca fonction u r welcome
    @Transient
    private final Person responsible;
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    public Request(int numberOfSeatsNeeded, int priority, Person person) {
        this.priority = priority;
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
        this.status = RequestStatus.PENDING;
        this.responsible = person;
    }

    public Request() {
        this.requestID = UUID.randomUUID();
        this.numberOfSeatsNeeded = 0;
        this.priority = 0;
        this.responsible = null;
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

    public Person getResponsible() {
        return responsible;
    }

    public void accept() {
        status = RequestStatus.ACCEPTED;
    }

    public void refuse() {
        status = RequestStatus.REFUSED;
    }

    private void annonce() {
    }
}