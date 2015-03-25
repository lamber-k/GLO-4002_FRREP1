package org.Marv1n.core.reservation;

import org.Marv1n.core.persistence.Repository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.room.Room;

public interface ReservationRepository extends Repository<Reservation> {

    boolean reservationExist(Reservation reservationSearched);

    Reservation findReservationByReservable(Room roomSearched) throws ReservationNotFoundException;

    Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
