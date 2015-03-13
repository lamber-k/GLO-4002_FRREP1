package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;

import java.util.Optional;

public interface IReservationFactory {

    public Optional<Reservation> reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
