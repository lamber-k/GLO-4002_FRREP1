package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public class Room implements IReservable {

    private int numberSeats;

    public Room(Integer numberOfSeats) {
        this.numberSeats = numberOfSeats;
    }

    @Override
    public int getNumberSeats() {
        return this.numberSeats;
    }

    @Override
    public boolean hasGreaterCapacityThan(IReservable reservable) {
        return this.getNumberSeats() >= reservable.getNumberSeats();
    }

    @Override
    public int compareReservableCapacity(IReservable reservable) {
        return this.getNumberSeats() - reservable.getNumberSeats();
    }

    @Override
    public boolean hasEnoughCapacity(Integer capacityNeeded) {
        return this.getNumberSeats() >= capacityNeeded;
    }
}
