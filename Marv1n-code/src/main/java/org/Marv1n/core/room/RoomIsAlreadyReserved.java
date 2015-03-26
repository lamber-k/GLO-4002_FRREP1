package org.Marv1n.core.room;

public class RoomIsAlreadyReserved extends Throwable {

    private static final long serialVersionUID = 42L;

    public RoomIsAlreadyReserved() {
        super("The room is already reserved");
    }
}
