package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;

public class FirstInFirstOutEvaluationStrategy implements EvaluationStrategy {

    @Override
    public Room evaluateOneRequest(RoomRepository reservables, Request request) {
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                return room;
            }
        }
        throw new EvaluationNoRoomFoundException();
    }
}
