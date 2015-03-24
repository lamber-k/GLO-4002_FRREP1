package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(ReservableRepository reservables, ReservationRepository reservations, Request evaluatedRequest) {
        Reservable betterReservable = null;
        for (Reservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable, reservations)) {
                betterReservable = getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableEvaluationResult(betterReservable);
    }

    private Reservable getBetterReservableOf(Reservable bestReservable, Reservable reservable) {
        if ((bestReservable == null) || bestReservable.hasGreaterOrEqualCapacityThan(reservable)) {
            return reservable;
        }
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, Reservable reservable, ReservationRepository reservations) {
        return reservableAvailable(reservations, reservable) && reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded());
    }

    private boolean reservableAvailable(ReservationRepository reservations, Reservable reservable) {
        try {
            reservations.findReservationByReservable(reservable);
        } catch (ReservationNotFoundException exception) {
            return true;
        }
        return false;
    }
}
