package core.reservation;

import core.request.Request;
import core.request.evaluation.ReservableEvaluationResult;
import core.room.Room;

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