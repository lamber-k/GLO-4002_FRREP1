package org.Marv1n.code.Reservable;

import java.util.UUID;

public class Room implements IReservable {

    private UUID roomID;
    private int numberSeats;

    public Room(Integer numberOfSeats) {
        roomID = UUID.randomUUID();
        this.numberSeats = numberOfSeats;
    }

    @Override
    public int getNumberSeats() {
        return numberSeats;
    }

    @Override
    public boolean hasGreaterCapacityThan(IReservable reservable) {
        return getNumberSeats() >= reservable.getNumberSeats();
    }

    @Override
    public int compareReservableCapacity(IReservable reservable) {
        return getNumberSeats() - reservable.getNumberSeats();
    }

    @Override
    public boolean hasEnoughCapacity(Integer capacityNeeded) {
        return getNumberSeats() >= capacityNeeded;
    }

    @Override
    public int hashCode() {
        return roomID.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Room) {
            return hashCode() == rhs.hashCode();
        }
        return (false);
    }

}
