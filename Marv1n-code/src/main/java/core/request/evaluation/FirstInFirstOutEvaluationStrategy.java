package core.request.evaluation;

import core.persistence.ReservationRepository;
import core.persistence.RoomRepository;
import core.request.Request;
import core.reservation.ReservationNotFoundException;
import core.room.Room;

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
