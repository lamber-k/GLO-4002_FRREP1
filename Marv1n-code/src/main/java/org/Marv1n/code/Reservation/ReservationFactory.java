package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;

import java.util.Optional;

public class ReservationFactory implements IReservationFactory {
    public Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult) {
        if (evaluationResult.matchFound()) {
            Reservation confirmReservation = new Reservation();
            try {
                confirmReservation.reserve(pendingRequest, evaluationResult.getBestReservableMatch());
                return Optional.of(confirmReservation);

            } catch (ExceptionReservableAlreadyBooked | ExceptionReservableInsufficientCapacity exceptionReservableAlreadyBooked) {
            }
        }
        return Optional.empty();
    }
}
