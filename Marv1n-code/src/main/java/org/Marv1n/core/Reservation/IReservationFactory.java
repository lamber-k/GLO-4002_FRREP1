package org.Marv1n.core.Reservation;

import org.Marv1n.core.EvaluationStrategy.ReservableEvaluationResult;
import org.Marv1n.core.Request.Request;

import java.util.Optional;

public interface IReservationFactory {

    Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
