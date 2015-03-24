package org.Marv1n.core.EvaluationStrategy;

import org.Marv1n.core.Room.RoomRepository;
import org.Marv1n.core.Reservation.ReservationRepository;
import org.Marv1n.core.Request.Request;

public interface EvaluationStrategy {

    ReservableEvaluationResult evaluateOneRequest(RoomRepository reservables, ReservationRepository reservations, Request request);
}
