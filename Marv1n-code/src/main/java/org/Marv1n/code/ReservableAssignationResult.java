package org.Marv1n.code;

import org.Marv1n.code.Reservable.Reservable;

public class ReservableAssignationResult implements AssignationResult {

    private Reservable matchingReservable = null;

    public ReservableAssignationResult(Reservable reservable) {
        this.matchingReservable = reservable;
    }

    public ReservableAssignationResult() {
    }

    @Override
    public boolean matchFound() {
        return this.matchingReservable != null;
    }

    public Reservable getBestReservableMatch() {
        return this.matchingReservable;
    }
}
