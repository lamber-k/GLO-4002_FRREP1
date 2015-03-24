package org.Marv1n.code.Reservation;

import org.Marv1n.code.EvaluationStrategy.ReservableEvaluationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;

import java.util.Optional;

public class ReservationFactory implements IReservationFactory {

    @Override
    public Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult) {
        if (!evaluationResult.matchFound()) {
            return Optional.empty();
        }
        Reservable reservableMatch = evaluationResult.getBestReservableMatch();
        if (!reservableMatch.hasEnoughCapacity(pendingRequest.getNumberOfSeatsNeeded())) {
            return Optional.empty();
        }
        Reservation confirmReservation = new Reservation(pendingRequest, reservableMatch);
        return Optional.of(confirmReservation);
    }
}