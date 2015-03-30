package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.RoomRepository;
import org.Marv1n.core.room.Room;

@FunctionalInterface
public interface EvaluationStrategy {

    Room evaluateOneRequest(RoomRepository reservables, Request request) throws EvaluationNoRoomFoundException;
}
