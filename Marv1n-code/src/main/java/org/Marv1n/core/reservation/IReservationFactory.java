package org.Marv1n.core.reservation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.evaluation.ReservableEvaluationResult;

import java.util.Optional;

@FunctionalInterface
public interface IReservationFactory {

    Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
