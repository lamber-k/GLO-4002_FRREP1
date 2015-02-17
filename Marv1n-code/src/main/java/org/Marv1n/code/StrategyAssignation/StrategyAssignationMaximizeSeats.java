package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
    protected IAssignationResult evaluateOneRequest(IReservableRepository reservables, Request evaluatedRequest) {
        IReservable betterReservable = null;

        for (IReservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable)) {
                betterReservable = this.getBetterReservableOf(betterReservable, reservable);
            }
        }
        return new ReservableAssignationResult(betterReservable);
    }

    private IReservable getBetterReservableOf(IReservable bestReservable, IReservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterCapacityThan(reservable))
            return reservable;
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, IReservable reservable) {
        return !(reservable.isBooked() || reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded()));
    }
}
