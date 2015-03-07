package org.Marv1n.code.Repository;

import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

public class ReservationRepository extends Repository<Reservation> implements IReservationRepository {
    @Override
    public boolean reservationExist(Reservation reservable) {
        return query().anyMatch(r -> r.equals(reservable));
    }

    @Override
    public boolean reservableAvailable(IReservable reservable) {
        return false;
    }
}
