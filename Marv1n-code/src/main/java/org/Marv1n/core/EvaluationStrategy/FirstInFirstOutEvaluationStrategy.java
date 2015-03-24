package org.Marv1n.core.EvaluationStrategy;

import org.Marv1n.core.Room.RoomRepository;
import org.Marv1n.core.Reservation.ReservationNotFoundException;
import org.Marv1n.core.Reservation.ReservationRepository;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Room.Room;

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
