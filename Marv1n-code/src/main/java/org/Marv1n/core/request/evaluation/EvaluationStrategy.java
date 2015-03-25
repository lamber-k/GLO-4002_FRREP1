package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.persistence.ReservationRepository;
import org.Marv1n.core.persistence.RoomRepository;

@FunctionalInterface
public interface EvaluationStrategy {

    ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request request);
}
