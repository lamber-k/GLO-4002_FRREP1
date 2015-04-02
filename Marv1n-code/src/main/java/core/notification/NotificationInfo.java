package core.notification;

import core.person.Person;

import java.util.List;

public class NotificationInfo {
    private final String category;
    private final String status;
    private final String identifier;
    private final String detail;
    private final List<Person> destination;

    public NotificationInfo(String category, String status, String identifier, String detail) {
        this.category = category;
        this.status = status;
        this.detail = detail;
        this.identifier = identifier;
        this.destination = null;
    }

    public NotificationInfo(String category, String status, String identifier, String detail, List<Person> destination) {
        this.category = category;
        this.status = status;
        this.detail = detail;
        this.identifier = identifier;
        this.destination = destination;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDetail() {
        return detail;
    }

    public List<Person> getDestination() {
        return destination;
    }
}
