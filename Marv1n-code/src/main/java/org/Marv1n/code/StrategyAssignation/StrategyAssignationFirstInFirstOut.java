package org.Marv1n.code.StrategyAssignation;

import org.Marv1n.code.AssignationResult;
import org.Marv1n.code.Repository.IReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.ReservableAssignationResult;
import org.Marv1n.code.Reservation;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(IReservableRepository reservables, Request request) {
        for (Reservable reservable : reservables.findAll())
            if (!reservable.isBooked()) {
                return new ReservableAssignationResult(reservable);
            }
        return new ReservableAssignationResult();
    }

}
