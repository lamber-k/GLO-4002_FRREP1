package org.Marv1n.core.notification;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public InvalidRequestException(String Message) {
        super(Message);
    }

}
