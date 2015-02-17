package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public class Room implements IReservable {

    private boolean booked;
    private Integer numberSeats;

    public Room(Integer numberOfSeats) {
        this.numberSeats = numberOfSeats;
        this.booked = false;
    }

    public Boolean isBooked() {
        return this.booked;
    }

    public void book(Request request) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        if (isBooked()) {
            throw new ExceptionReservableAlreadyBooked();
        }
        if (!this.hasEnoughCapacity(request.getNumberOfSeatsNeeded())) {
            throw new ExceptionReservableInsufficientCapacity();
        }
        this.booked = true;
    }

    public Integer getNumberSeats() {
        return this.numberSeats;
    }

    public boolean hasGreaterCapacityThan(IReservable reservable) {

        return this.getNumberSeats() >= reservable.getNumberSeats();
    }

    @Override
    public Integer compareReservableCapacity(IReservable reservable) {
        return this.getNumberSeats() - reservable.getNumberSeats();
    }

    @Override
    public boolean hasEnoughCapacity(Integer capacityNeeded) {
        return this.getNumberSeats() >= capacityNeeded;
    }
}
