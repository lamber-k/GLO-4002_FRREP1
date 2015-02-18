package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.IReservable;

import java.util.UUID;

public class Reservation {
    private UUID reservationID;
    private Request request;
    private IReservable reserved;

    public Reservation() {
        this.reservationID = UUID.randomUUID();
    }

    public void reserve(Request request, IReservable reserved) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        reserved.book(request);
        this.request = request;
        this.reserved = reserved;
    }


    public UUID getReservationID() {
        return this.reservationID;
    }

    public Request getRequest() {
        return this.request;
    }

    public IReservable getReserved() {
        return this.reserved;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Reservation) {
            return this.reservationID.equals(((Reservation) rhs).reservationID);
        }
        return false;
    }

}