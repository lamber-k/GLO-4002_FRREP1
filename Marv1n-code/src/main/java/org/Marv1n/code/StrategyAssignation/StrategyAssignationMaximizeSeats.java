package org.Marv1n.code.StrategyAssignation;

<<<<<<< HEAD
import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
=======
import org.Marv1n.code.AssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Reservable;
>>>>>>> Reservation System. Accepted ?
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
<<<<<<< HEAD
    protected IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request evaluatedRequest) {
        IReservable betterIReservable = null;

        for (IReservable IReservable : IReservables) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, IReservable)) {
                betterIReservable = this.getBetterReservableOf(betterIReservable, IReservable);
=======
    protected AssignationResult evaluateOneRequest(IReservableRepository reservables, Request evaluatedRequest) {
        Reservable betterReservable = null;

        for (Reservable reservable : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, reservable)) {
                betterReservable = this.getBetterReservableOf(betterReservable, reservable);
>>>>>>> Reservation System. Accepted ?
            }
        }
        return new ReservableAssignationResult(betterIReservable);
    }

<<<<<<< HEAD
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
=======
    private Reservable getBetterReservableOf(Reservable bestReservable, Reservable reservable) {
        if (bestReservable == null || bestReservable.hasGreaterCapacityThan(reservable))
            return reservable;
        return bestReservable;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, Reservable reservable) {
        return !(reservable.isBooked() || reservable.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded()));
>>>>>>> Reservation System. Accepted ?
    }
}
