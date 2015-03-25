package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.ReservationRepository;
import org.Marv1n.core.room.RoomRepository;

@FunctionalInterface
public interface EvaluationStrategy {

    ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request request);
}
