package org.Marv1n.code;

import java.util.List;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(List<Reservable> reservables, Request request) {
        for (Reservable reservable : reservables)
            if (!reservable.isBooked()) {
                return new ReservableAssignationResult(reservable);
            }
        return new ReservableAssignationResult();
    }

    @Override
    protected void treatAssignationResult(AssignationResult result, Request evaluatedRequest) {
        ReservableAssignationResult ReservableAssignationResult = (ReservableAssignationResult) result;
        ReservableAssignationResult.getBestReservableMatch().book(evaluatedRequest);
    }
}
