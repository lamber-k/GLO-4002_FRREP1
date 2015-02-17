package org.Marv1n.code.StrategyAssignation;

<<<<<<< HEAD
import org.Marv1n.code.IAssignationResult;
=======
import org.Marv1n.code.AssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
>>>>>>> Reservation System. Accepted ?
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
<<<<<<< HEAD
    protected IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request request) {
        for (IReservable IReservable : IReservables)
            if (!IReservable.isBooked()) {
                return new ReservableAssignationResult(IReservable);
=======
    protected AssignationResult evaluateOneRequest(IReservableRepository reservables, Request request) {
        for (Reservable reservable : reservables.findAll())
            if (!reservable.isBooked()) {
                return new ReservableAssignationResult(reservable);
>>>>>>> Reservation System. Accepted ?
            }
        return new ReservableAssignationResult();
    }

<<<<<<< HEAD
    @Override
    protected void treatAssignationResult(IAssignationResult result, Request evaluatedRequest) {
        ReservableAssignationResult ReservableAssignationResult = (ReservableAssignationResult) result;
        ReservableAssignationResult.getBestReservableMatch().book(evaluatedRequest);
    }
=======
>>>>>>> Reservation System. Accepted ?
}
