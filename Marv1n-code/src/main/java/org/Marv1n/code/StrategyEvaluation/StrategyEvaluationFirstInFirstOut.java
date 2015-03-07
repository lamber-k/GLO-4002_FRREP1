package org.Marv1n.code.StrategyEvaluation;

import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Repository.IReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;

public class StrategyEvaluationFirstInFirstOut implements IStrategyEvaluation {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(IReservableRepository reservables, IReservationRepository reservations, Request request) {
        for (IReservable reservable : reservables.findAll())
            if (!reservations.reservableAvailable(reservable)) {
                return new ReservableEvaluationResult(reservable);
            }
        return new ReservableEvaluationResult();
    }

}
