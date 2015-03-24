package org.Marv1n.core.Notification;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public InvalidRequestException(String Message) {
        super(Message);
    }

}
