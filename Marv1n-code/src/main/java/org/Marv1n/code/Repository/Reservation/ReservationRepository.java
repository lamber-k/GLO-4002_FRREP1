package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

public interface ReservationRepository extends Repository<Reservation> {

    public boolean reservationExist(Reservation reservationSearched);

    public Reservation findReservationByReservable(IReservable reservableSearched) throws ReservationNotFoundException;

    public Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
