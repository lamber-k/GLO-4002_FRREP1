package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;

@FunctionalInterface
public interface EvaluationStrategy {

    Room evaluateOneRequest(RoomRepository reservables, Request request);
}
