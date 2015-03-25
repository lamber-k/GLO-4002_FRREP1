package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.reservation.ReservationRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomRepository;

public class FirstInFirstOutEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request request) {
        for (Room room : reservables.findAll()) {
            if (reservableAvailable(reservations, room)) {
                return new ReservableEvaluationResult(room);
            }
        }
        return new ReservableEvaluationResult();
    }

    private boolean reservableAvailable(ReservationRepository reservations, Room room) {
        try {
            reservations.findReservationByReservable(room);
        } catch (ReservationNotFoundException exception) {
            return true;
        }
        return false;
    }
}
