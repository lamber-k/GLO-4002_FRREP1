package ca.ulaval.glo4002.core.room;

public class RoomAlreadyReservedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RoomAlreadyReservedException() {
        super("The room is already reserved");
    }
}
