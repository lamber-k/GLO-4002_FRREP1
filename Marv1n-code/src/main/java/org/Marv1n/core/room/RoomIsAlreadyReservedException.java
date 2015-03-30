package org.Marv1n.core.room;

public class RoomIsAlreadyReservedException extends Throwable {

    private static final long serialVersionUID = 42L;

    public RoomIsAlreadyReservedException() {
        super("The room is already reserved");
    }
}
