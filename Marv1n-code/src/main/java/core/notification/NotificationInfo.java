package core.notification;

import core.person.Person;

import java.util.List;

public class NotificationInfo {
    public final String category;
    public final String status;
    public final String identifier;
    public final String detail;
    public final List<Person> destination;

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
}
