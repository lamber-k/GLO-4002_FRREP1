package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomRepository;

public class FirstInFirstOutEvaluationStrategy implements EvaluationStrategy {

    @Override
    public Room evaluateOneRequest(RoomRepository reservables, Request request) throws EvaluationNoRoomFoundException {
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                return room;
            }
        }
        throw new EvaluationNoRoomFoundException();
    }
}
