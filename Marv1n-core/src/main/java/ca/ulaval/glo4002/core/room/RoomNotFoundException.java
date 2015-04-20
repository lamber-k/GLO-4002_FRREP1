package ca.ulaval.glo4002.core.room;

public class RoomNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RoomNotFoundException() {

    }

    public RoomNotFoundException(Exception exception) {
        super(exception);
    }
}