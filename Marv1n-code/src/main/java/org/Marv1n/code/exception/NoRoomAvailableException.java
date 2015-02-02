package org.Marv1n.code.exception;

public class NoRoomAvailableException extends RuntimeException {
    public NoRoomAvailableException() {
        super("No room are available");
    }
}
