package core.room;

public class RoomAlreadyReservedException extends Throwable {

    public RoomAlreadyReservedException() {
        super("The room is already reserved");
    }
}
