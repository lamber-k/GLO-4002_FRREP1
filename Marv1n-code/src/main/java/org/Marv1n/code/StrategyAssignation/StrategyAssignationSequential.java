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
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;
>>>>>>> Reservation System. Accepted ?

import java.util.Iterator;
import java.util.List;

public abstract class StrategyAssignationSequential implements IStrategyAssignation {

    @Override
<<<<<<< HEAD
    public void assignReservables(List<Request> requests, List<IReservable> IReservables) {
=======
    public void assignReservables(List<Request> requests, IReservableRepository reservables) {
>>>>>>> Reservation System. Accepted ?
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            IAssignationResult result = this.evaluateOneRequest(IReservables, evaluatedRequest);
            if (result.matchFound()) {
                try {
                    this.treatAssignationResult(result, evaluatedRequest);
                } catch (ExceptionAssignationNoMatchFound|ExceptionReservableAlreadyBooked|ExceptionReservableInsufficientCapacity ex) {
                    continue;
                }
                iterator.remove();
            }
        }
    }

<<<<<<< HEAD
    protected abstract IAssignationResult evaluateOneRequest(List<IReservable> IReservables, Request request);

    protected abstract void treatAssignationResult(IAssignationResult result, Request request);
=======
    protected abstract AssignationResult evaluateOneRequest(IReservableRepository reservables, Request request);

    protected Reservation treatAssignationResult(AssignationResult result, Request evaluatedRequest) throws ExceptionAssignationNoMatchFound, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        if (result.matchFound()) {
            ReservableAssignationResult reservableResult = (ReservableAssignationResult)result;
            Reservation confirmReservation = new Reservation();
            confirmReservation.reserve(evaluatedRequest, reservableResult.getBestReservableMatch());

            return (confirmReservation);
        }
        throw new ExceptionAssignationNoMatchFound();
    }
>>>>>>> Reservation System. Accepted ?
}
