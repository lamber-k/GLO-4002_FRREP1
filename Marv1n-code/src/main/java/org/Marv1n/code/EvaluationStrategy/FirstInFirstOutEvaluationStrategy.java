package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;

public class FirstInFirstOutEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(ReservableRepository reservables, ReservationRepository reservations, Request request) {
        for (Reservable reservable : reservables.findAll()) {
            if (reservableAvailable(reservations, reservable)) {
                return new ReservableEvaluationResult(reservable);
            }
        }
        return new ReservableEvaluationResult();
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
