package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.ReservableAssignationResult;

import java.util.List;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
    protected IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request request) {
        for (IReservable IReservable : IReservables)
            if (!IReservable.isBooked()) {
                return new ReservableAssignationResult(IReservable);
            }
        return new ReservableAssignationResult();
    }

    @Override
    protected void treatAssignationResult(IAssignationResult result, Request evaluatedRequest) {
        ReservableAssignationResult ReservableAssignationResult = (ReservableAssignationResult) result;
        ReservableAssignationResult.getBestReservableMatch().book(evaluatedRequest);
    }
}
