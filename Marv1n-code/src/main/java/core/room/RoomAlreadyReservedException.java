package core.room;

public class RoomAlreadyReservedException extends RuntimeException {

    public RoomAlreadyReservedException() {
        super("The room is already reserved");
    }
}
