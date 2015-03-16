package org.Marv1n.code.Reservable;

import java.util.UUID;

public class Room implements IReservable {

    private final UUID roomID;
    private int numberOfSeats;
    private String name;

    public Room(int numberOfSeats, String name) {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
        this.name = name;
    }

    @Override
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasGreaterOrEqualCapacityThan(IReservable reservable) {
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
