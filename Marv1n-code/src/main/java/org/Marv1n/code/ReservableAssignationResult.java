package org.Marv1n.code;

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
