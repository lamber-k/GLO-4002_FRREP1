package core.notification;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public InvalidRequestException(String message) {
        super(message);
    }

}
