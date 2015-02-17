package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.IAssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.ReservableAssignationResult;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
    protected IAssignationResult evaluateOneRequest(IReservableRepository reservables, Request request) {
        for (IReservable reservable : reservables.findAll())
            if (!reservable.isBooked()) {
                return new ReservableAssignationResult(reservable);
            }
        return new ReservableAssignationResult();
    }

}
