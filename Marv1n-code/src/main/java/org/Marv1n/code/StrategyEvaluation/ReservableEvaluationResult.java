package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Reservable.IReservable;

public class ReservableEvaluationResult {

    private IReservable matchingIReservable = null;

    public ReservableEvaluationResult(IReservable IReservable) {
        matchingIReservable = IReservable;
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
