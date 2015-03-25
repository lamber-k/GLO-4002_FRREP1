package org.Marv1n.core.reservation;

import org.Marv1n.core.request.evaluation.ReservableEvaluationResult;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.Room;

import java.util.Optional;

public class ReservationFactory implements IReservationFactory {

    @Override
    public Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult) {
        if (!evaluationResult.matchFound()) {
            return Optional.empty();
        }
        Room roomMatch = evaluationResult.getBestReservableMatch();
        if (!roomMatch.hasEnoughCapacity(pendingRequest.getNumberOfSeatsNeeded())) {
            return Optional.empty();
        }
        Reservation confirmReservation = new Reservation(pendingRequest, roomMatch);
        return Optional.of(confirmReservation);
    }
}