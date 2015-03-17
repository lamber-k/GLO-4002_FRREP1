package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Reservable.Reservable;

public class ReservableEvaluationResult {

    private Reservable matchingReservable = null;

    public ReservableEvaluationResult(Reservable Reservable) {
        this.matchingReservable = Reservable;
    }

    public ReservableEvaluationResult() {
    }

    public boolean matchFound() {
        return matchingReservable != null;
    }

    public Reservable getBestReservableMatch() {
        return matchingReservable;
    }
}
