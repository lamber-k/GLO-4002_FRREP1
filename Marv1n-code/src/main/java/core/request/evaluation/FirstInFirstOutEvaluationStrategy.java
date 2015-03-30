package core.request.evaluation;

import core.request.Request;
import core.room.Room;
import core.room.RoomRepository;

// TODO ALL DELETE ?
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
