package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;

import java.util.Optional;

public class ReservationFactory implements IReservationFactory {
    public Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult) {
        if (!evaluationResult.matchFound())
            return Optional.empty();
        IReservable reservableMatch = evaluationResult.getBestReservableMatch();
        if (!reservableMatch.hasEnoughCapacity(pendingRequest.getNumberOfSeatsNeeded()))
            return Optional.empty();
        Reservation confirmReservation = new Reservation(pendingRequest, reservableMatch);
        return Optional.of(confirmReservation);
    }
}