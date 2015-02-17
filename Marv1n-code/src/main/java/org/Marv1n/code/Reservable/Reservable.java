package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public interface Reservable {

    public Boolean isBooked();

    public void book(Request request) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity;

    public Integer getNumberSeats();

    public boolean hasGreaterCapacityThan(Reservable reservable);

    public Integer compareReservableCapacity(Reservable reservable);

    public boolean hasEnoughCapacity(Integer capacityNeeded);
}
