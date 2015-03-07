package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Request;

public interface IStrategyEvaluation {

    public abstract ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, IReservationRepository reservations, Request request);

}
