package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(ReservableRepository reservables, ReservationRepository reservations, Request evaluatedRequest) {
        IReservable betterReservable = null;
        for (IReservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable, reservations)) {
                betterReservable = getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableEvaluationResult(betterReservable);
    }

    private IReservable getBetterReservableOf(IReservable bestReservable, IReservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterOrEqualCapacityThan(reservable)) {
            return reservable;
        }
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable reservable, ReservationRepository reservations) {
        return reservableAvailable(reservations, reservable) && reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded());
    }

    private boolean reservableAvailable(ReservationRepository reservations, IReservable reservable) {
        try {
            reservations.findReservationByReservable(reservable);
        } catch (ReservationNotFoundException exception) {
            return true;
        }
        return false;
    }
}
