package org.Marv1n.code;

import org.Marv1n.code.Reservable.IReservable;

public class ReservableAssignationResult implements IAssignationResult {

    private IReservable matchingIReservable = null;

    public ReservableAssignationResult(IReservable IReservable) {
        this.matchingIReservable = IReservable;
    }

    public ReservableAssignationResult() {
    }

    @Override
    public boolean matchFound() {
        return this.matchingIReservable != null;
    }

    public IReservable getBestReservableMatch() {
        return this.matchingIReservable;
    }
}
