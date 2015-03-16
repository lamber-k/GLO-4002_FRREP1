package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class StrategyEvaluationMaximizeSeats implements IStrategyEvaluation {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, IReservationRepository reservations, Request evaluatedRequest) {
        IReservable betterReservable = null;

        for (IReservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable, reservations)) {
                betterReservable = getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableEvaluationResult(betterReservable);
    }

    private IReservable getBetterReservableOf(IReservable bestReservable, IReservable reservable) {
        if (bestReservable == null) {
            return reservable;
        }
        else if (bestReservable.hasGreaterOrEqualCapacityThan(reservable)) {
            return reservable;
        }
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable reservable, IReservationRepository reservations) {
        return reservableAvailable(reservations, reservable) && reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded());
    }

    private boolean reservableAvailable(IReservationRepository reservations, IReservable reservable) {
        try {
            reservations.findReservationByReservable(reservable);
        } catch (ReservationNotFoundException e) {
            return (true);
        }
        return (false);
    }
}
