package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class StrategyEvaluationMaximizeSeats implements IStrategyEvaluation {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, IReservationRepository reservations, Request evaluatedRequest) {
        IReservable betterReservable = null;

        for (IReservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable, reservations)) {
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

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable reservable, IReservationRepository reservations) {
        return !(reservations.reservableAvailable(reservable) || reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded()));
    }
}
