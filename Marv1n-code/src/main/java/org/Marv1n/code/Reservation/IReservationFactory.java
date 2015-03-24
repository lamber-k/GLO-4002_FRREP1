package org.Marv1n.code.Reservation;

import org.Marv1n.code.EvaluationStrategy.ReservableEvaluationResult;
import org.Marv1n.code.Request;

import java.util.Optional;

public interface IReservationFactory {

    Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
