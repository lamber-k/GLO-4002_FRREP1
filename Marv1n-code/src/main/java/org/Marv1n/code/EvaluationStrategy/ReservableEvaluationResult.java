package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Reservable.IReservable;

public class ReservableEvaluationResult {

    private IReservable matchingIReservable = null;

    public ReservableEvaluationResult(IReservable IReservable) {
        this.matchingIReservable = IReservable;
    }

    public ReservableEvaluationResult() {
    }

    public boolean matchFound() {
        return matchingIReservable != null;
    }

    public IReservable getBestReservableMatch() {
        return matchingIReservable;
    }
}
