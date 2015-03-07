package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

public interface IReservationRepository extends IRepository<Reservation> {
    public boolean reservationExist(Reservation reservation);
    public boolean reservableAvailable(IReservable reservable);
}
