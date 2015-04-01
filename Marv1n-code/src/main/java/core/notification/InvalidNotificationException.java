package core.notification;

public class InvalidNotificationException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public InvalidNotificationException(String message) {
        super(message);
    }

}
