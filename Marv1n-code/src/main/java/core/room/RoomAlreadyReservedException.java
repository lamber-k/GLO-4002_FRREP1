package core.room;

public class RoomAlreadyReservedException extends Throwable {

    private static final long serialVersionUID = 42L;

    public RoomAlreadyReservedException() {
        super("The room is already reserved");
    }
}
