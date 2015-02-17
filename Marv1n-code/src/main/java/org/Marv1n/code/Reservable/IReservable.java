package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public interface IReservable {

    public Boolean isBooked();

    public void book(Request request) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity;

    public Integer getNumberSeats();

    public boolean hasGreaterCapacityThan(IReservable reeservable);

    public Integer compareReservableCapacity(IReservable reservable);

    public boolean hasEnoughCapacity(Integer capacityNeeded);
}
