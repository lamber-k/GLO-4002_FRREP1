package core.request.evaluation;

import core.persistence.ReservationRepository;
import core.persistence.RoomRepository;
import core.request.Request;

@FunctionalInterface
public interface EvaluationStrategy {

    ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request request);
}
