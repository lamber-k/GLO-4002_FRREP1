package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.persistence.RoomRepository;

public class FirstInFirstOutEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, Request request) {
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                return new ReservableEvaluationResult(room);
            }
        }
        return new ReservableEvaluationResult();
    }
}
