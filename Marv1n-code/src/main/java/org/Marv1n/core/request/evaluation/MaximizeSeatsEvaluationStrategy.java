package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.persistence.RoomRepository;
import org.Marv1n.core.room.RoomInsufficientSeats;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, Request evaluatedRequest) {
        Room betterRoom = null;
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                if (betterRoom == null && room.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded())) {
                    betterRoom = room;
                } else {
                    try {
                        betterRoom = room.getBestFit(betterRoom, evaluatedRequest.getNumberOfSeatsNeeded());
                    } catch (RoomInsufficientSeats roomInsufficientSeats) {
                        continue;
                    }
                }
            }
        }
        return new ReservableEvaluationResult(betterRoom);
    }
}
