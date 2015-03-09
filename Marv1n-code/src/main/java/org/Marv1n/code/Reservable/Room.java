package org.Marv1n.code.Reservable;

import java.util.UUID;

public class Room implements IReservable {

    private UUID roomID;
    private int numberOfSeats;

    public Room(int numberOfSeats) {
        roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public boolean hasGreaterCapacityThan(IReservable reservable) {
        return getNumberOfSeats() >= reservable.getNumberOfSeats();
    }

    @Override
    public int compareReservableCapacity(IReservable reservable) {
        return getNumberOfSeats() - reservable.getNumberOfSeats();
    }

    @Override
    public boolean hasEnoughCapacity(int capacityNeeded) {
        return getNumberOfSeats() >= capacityNeeded;
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
