package org.Marv1n.core.Reservation;

import org.Marv1n.core.Persistence.Repository;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Room.Room;

public interface ReservationRepository extends Repository<Reservation> {

    boolean reservationExist(Reservation reservationSearched);

    Reservation findReservationByReservable(Room roomSearched) throws ReservationNotFoundException;

    Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
