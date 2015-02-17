package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.AssignationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.ReservableAssignationResult;

import java.util.List;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(List<Reservable> reservables, Request evaluatedRequest) {
        Reservable betterReservable = null;

        for (Reservable reservable : reservables) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable)) {
                betterReservable = this.getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableAssignationResult(betterReservable);
    }

    @Override
    protected void treatAssignationResult(AssignationResult result, Request evaluatedRequest) {
        ReservableAssignationResult ReservableAssignationResult = (ReservableAssignationResult) result;
        ReservableAssignationResult.getBestReservableMatch().book(evaluatedRequest);
    }

    private Reservable getBetterReservableOf(Reservable bestReservable, Reservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterCapacityThan(reservable))
            return reservable;
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, Reservable reservable) {
        return !(reservable.isBooked() || evaluatedRequest.getNumberOdSeatsNeeded() > reservable.getNumberSeats());
    }
}
