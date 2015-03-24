package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.Reservation.Reservation;

public interface ReservationRepository extends Repository<Reservation> {

    boolean reservationExist(Reservation reservationSearched);

    Reservation findReservationByReservable(Reservable reservableSearched) throws ReservationNotFoundException;

    Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
