package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class Reservation {
    private Request request;
    private IReservable reserved;

    public Reservation(Request request, IReservable reserved) {
        this.request = request;
        this.reserved = reserved;
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
            return hashCode() == rhs.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.request.hashCode();
        hash = hash * 13 + this.reserved.hashCode();
        return hash;
    }

}
