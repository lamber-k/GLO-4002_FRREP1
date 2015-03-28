package core.reservation;

import core.request.Request;
import core.request.evaluation.ReservableEvaluationResult;

import java.util.Optional;

public interface IReservationFactory {

    Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
