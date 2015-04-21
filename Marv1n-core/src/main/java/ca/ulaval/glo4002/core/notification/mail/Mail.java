package ca.ulaval.glo4002.core.notification.mail;

import java.util.List;

public class Mail {

    private final String from;
    private final List<String> to;
    private final String object;
    private final String message;

    public Mail(String from, List<String> to, String object, String message) {
        this.from = from;
        this.to = to;
        this.object = object;
        this.message = message;
    }

    @Override
    public int hashCode() {
        //TODO ALL Test me properly
        return 3 * message.hashCode() + 5 * from.hashCode() + 7 * to.hashCode() + 11 * object.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        //TODO ALL Test me properly
        return rhs != null && rhs instanceof Mail && hashCode() == rhs.hashCode();
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public String getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }
}
