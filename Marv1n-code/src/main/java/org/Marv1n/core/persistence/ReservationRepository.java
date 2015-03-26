package org.Marv1n.core.persistence;

import org.Marv1n.core.request.Request;
import org.Marv1n.core.reservation.Reservation;
import org.Marv1n.core.reservation.ReservationNotFoundException;
import org.Marv1n.core.room.Room;

public interface ReservationRepository extends Repository<Reservation> {

    boolean reservationExist(Reservation reservationSearched);

    Reservation findReservationByReservable(Room roomSearched) throws ReservationNotFoundException;

    Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
