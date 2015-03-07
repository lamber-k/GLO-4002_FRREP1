package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Repository.IRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

public interface IReservationRepository extends IRepository<Reservation> {
    public boolean reservationExist(Reservation reservationSearched);
    public Reservation findReservationByReservable(IReservable reservableSearched) throws ReservationNotFoundException;
    public Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException;
}
