package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class StrategyEvaluationMaximizeSeats implements IStrategyEvaluation {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, Request evaluatedRequest) {
        IReservable betterReservable = null;

        for (IReservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable)) {
                betterReservable = this.getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableEvaluationResult(betterReservable);
    }

    private IReservable getBetterReservableOf(IReservable bestReservable, IReservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterCapacityThan(reservable))
            return reservable;
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable reservable) {
        return !(reservable.isBooked() || reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded()));
    }
}
