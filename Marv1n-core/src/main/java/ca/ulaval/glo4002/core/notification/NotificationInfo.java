package ca.ulaval.glo4002.core.notification;

import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.RequestStatus;

import java.util.List;
import java.util.UUID;

public class NotificationInfo {

    private final RequestStatus status;
    private final UUID identifier;
    private final List<Person> destination;
    private final String reason;

    public NotificationInfo(RequestStatus status, UUID identifier, String reason, List<Person> destination) {
        this.status = status;
        this.reason = reason;
        this.identifier = identifier;
        this.destination = destination;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public List<Person> getDestination() {
        return destination;
    }

    public String getReason() {
        return this.reason;
    }
}
