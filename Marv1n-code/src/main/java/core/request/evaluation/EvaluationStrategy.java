package core.request.evaluation;

import core.request.Request;
import core.room.Room;
import core.room.RoomRepository;

@FunctionalInterface
public interface EvaluationStrategy {

    Room evaluateOneRequest(RoomRepository reservables, Request request) throws EvaluationNoRoomFoundException;
}
