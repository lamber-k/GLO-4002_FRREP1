package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;

public class Reservation {

    private Request request;
    private Reservable reserved;

    public Reservation(Request request, Reservable reserved) {
        this.request = request;
        this.reserved = reserved;
    }

    public Request getRequest() {
        return request;
    }

    public Reservable getReserved() {
        return reserved;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Reservation) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (request.hashCode() * 3) + (reserved.hashCode() * 5);
    }
}
