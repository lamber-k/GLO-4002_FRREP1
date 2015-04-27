package ca.ulaval.glo4002.core.notification;

public class InvalidNotificationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidNotificationException(Throwable exception) {
        super(exception);
    }
}
