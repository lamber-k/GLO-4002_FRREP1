package core.persistence;

import core.request.Request;
import core.reservation.Reservation;
import core.reservation.ReservationNotFoundException;
import core.room.Room;

public interface ReservationRepository extends Repository<Reservation> {

    boolean reservationExist(Reservation reservationSearched);

    Reservation findReservationByReservable(Room roomSearched) throws ReservationNotFoundException;

    Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
