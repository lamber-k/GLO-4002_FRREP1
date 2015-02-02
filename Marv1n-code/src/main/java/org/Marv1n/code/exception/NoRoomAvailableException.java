package org.Marv1n.code.exception;

/**
 * Created by Rafaël on 27/01/2015.
 */
public class NoRoomAvailableException extends RuntimeException {
    public NoRoomAvailableException() {
        super("No room are available");
    }
}
