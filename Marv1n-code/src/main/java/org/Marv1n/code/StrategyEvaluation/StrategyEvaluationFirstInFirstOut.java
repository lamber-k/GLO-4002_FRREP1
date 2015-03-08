package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.Reservable.IReservableRepository;
import org.Marv1n.code.Repository.Reservation.IReservationRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class StrategyEvaluationFirstInFirstOut implements IStrategyEvaluation {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, IReservationRepository reservations, Request request) {
        for (IReservable reservable : reservables.findAll()) {
            if (reservableAvailable(reservations, reservable)) {
                return new ReservableEvaluationResult(reservable);
            }
        }
        return new ReservableEvaluationResult();
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
