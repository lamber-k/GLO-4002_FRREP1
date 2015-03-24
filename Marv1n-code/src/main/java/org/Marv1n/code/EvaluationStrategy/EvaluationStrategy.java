package org.Marv1n.code.EvaluationStrategy;

import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;

public interface EvaluationStrategy {

    ReservableEvaluationResult evaluateOneRequest(ReservableRepository reservables, ReservationRepository reservations, Request request);
}
