package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.AssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(IReservableRepository reservables, Request evaluatedRequest) {
        Reservable betterReservable = null;

        for (Reservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable)) {
                betterReservable = this.getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableAssignationResult(betterReservable);
    }

    private Reservable getBetterReservableOf(Reservable bestReservable, Reservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterCapacityThan(reservable))
            return reservable;
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, Reservable reservable) {
        return !(reservable.isBooked() || reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded()));
    }
}
