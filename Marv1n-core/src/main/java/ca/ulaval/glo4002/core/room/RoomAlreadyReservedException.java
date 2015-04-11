package ca.ulaval.glo4002.core.room;

public class RoomAlreadyReservedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RoomAlreadyReservedException() {
        super("Cette salle est déjà reservé");
    }
}
