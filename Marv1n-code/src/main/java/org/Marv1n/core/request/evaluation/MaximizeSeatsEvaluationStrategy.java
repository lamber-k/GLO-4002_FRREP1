package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.reservation.ReservationRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomRepository;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request evaluatedRequest) {
        Room betterRoom = null;
        for (Room room : reservables.findAll()) {
            if (doesTheReservableFitsTheRequest(evaluatedRequest, room, reservations)) {
                betterRoom = getBetterReservableOf(betterRoom, room);
            }
        }
        return new ReservableEvaluationResult(betterRoom);
    }

    private Room getBetterReservableOf(Room bestRoom, Room room) {
        if ((bestRoom == null) || bestRoom.hasGreaterOrEqualCapacityThan(room)) {
            return room;
        }
        return bestRoom;
    }

    private boolean doesTheReservableFitsTheRequest(Request evaluatedRequest, Room room, ReservationRepository reservations) {
        return reservableAvailable(reservations, room) && room.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded());
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
