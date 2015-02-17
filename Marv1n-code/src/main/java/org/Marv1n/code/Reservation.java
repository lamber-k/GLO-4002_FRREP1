package org.Marv1n.code;

import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Reservable;

import java.util.UUID;

public class Reservation {
    private UUID reservationID;
    private Request request;
    private Reservable reserved;

    public Reservation() {
        this.reservationID = UUID.randomUUID();
    }

    public void reserve(Request request, Reservable reserved) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
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

    public Reservable getReserved() {
        return this.reserved;
    }

    @Override
    public boolean  equals(Object rhs) {
        if (rhs == null) {
            return false;
        }
        else if (rhs instanceof Reservation) {
            return this.reservationID.equals(((Reservation) rhs).reservationID);
        }
        return false;
    }

}
