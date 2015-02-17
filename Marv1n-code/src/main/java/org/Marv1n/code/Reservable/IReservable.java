package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public interface IReservable {

    public Boolean isBooked();

    public void book(Request request);

    public Request getRequest();

    public Integer getNumberSeats();

    public boolean hasGreaterCapacityThan(IReservable IReservable);
}
