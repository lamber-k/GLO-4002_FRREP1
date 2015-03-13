package org.Marv1n.code.Notifaction;

public class InvalidRequestException extends RuntimeException {

    private final String reason;

    public InvalidRequestException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return (reason);
    }
}
