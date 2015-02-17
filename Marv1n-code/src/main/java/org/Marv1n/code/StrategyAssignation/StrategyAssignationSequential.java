package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

import java.util.Iterator;
import java.util.List;

public abstract class StrategyAssignationSequential implements IStrategyAssignation {

    @Override
    public void assignReservables(List<Request> requests, IReservableRepository reservables) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            IAssignationResult result = this.evaluateOneRequest(reservables, evaluatedRequest);
            if (result.matchFound()) {
                try {
                    this.treatAssignationResult(result, evaluatedRequest);
                } catch (ExceptionAssignationNoMatchFound | ExceptionReservableAlreadyBooked | ExceptionReservableInsufficientCapacity ex) {
                    continue;
                }
                iterator.remove();
            }
        }
    }

    protected abstract IAssignationResult evaluateOneRequest(IReservableRepository reservables, Request request);

    protected Reservation treatAssignationResult(IAssignationResult result, Request evaluatedRequest) throws ExceptionAssignationNoMatchFound, ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        if (result.matchFound()) {
            ReservableAssignationResult reservableResult = (ReservableAssignationResult) result;
            Reservation confirmReservation = new Reservation();
            confirmReservation.reserve(evaluatedRequest, reservableResult.getBestReservableMatch());

            return (confirmReservation);
        }
        throw new ExceptionAssignationNoMatchFound();
    }
}
