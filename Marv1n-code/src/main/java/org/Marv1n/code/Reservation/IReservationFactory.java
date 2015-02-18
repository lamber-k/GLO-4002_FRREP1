package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.StrategyEvaluation.ReservableEvaluationResult;

import java.util.Optional;

/**
 * Created by nate on 15-02-17.
 */
public interface IReservationFactory {
    public Optional<Reservation> Reserve(Request pendingRequest, ReservableEvaluationResult evaluationResult);
}
