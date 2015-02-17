package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.ReservableAssignationResult;

import java.util.List;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
    protected IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request evaluatedRequest) {
        IReservable betterIReservable = null;

        for (IReservable IReservable : IReservables) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, IReservable)) {
                betterIReservable = this.getBetterReservableOf(betterIReservable, IReservable);
            }
        }
        return new ReservableAssignationResult(betterIReservable);
    }

    @Override
    protected void treatAssignationResult(IAssignationResult result, Request evaluatedRequest) {
        ReservableAssignationResult ReservableAssignationResult = (ReservableAssignationResult) result;
        ReservableAssignationResult.getBestReservableMatch().book(evaluatedRequest);
    }

    private IReservable getBetterReservableOf(IReservable bestIReservable, IReservable IReservable) {
        if (bestIReservable == null || bestIReservable.hasGreaterCapacityThan(IReservable))
            return IReservable;
        return bestIReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable IReservable) {
        return !(IReservable.isBooked() || evaluatedRequest.getNumberOdSeatsNeeded() > IReservable.getNumberSeats());
    }
}
