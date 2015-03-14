package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Repository.RepositoryInMemory;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;

import java.util.Optional;

public class ReservationRepositoryInMemory extends RepositoryInMemory<Reservation> implements IReservationRepository {

    @Override
    public boolean reservationExist(Reservation reservable) {
        return query().anyMatch(r -> r.equals(reservable));
    }

    @Override
    public Reservation findReservationByReservable(IReservable reservableSearched) throws ReservationNotFoundException {
        Optional<Reservation> reservationFound = query().filter(r -> r.getReserved().equals(reservableSearched)).findFirst();

        if (!reservationFound.isPresent())
            throw new ReservationNotFoundException();
        return (reservationFound.get());
    }

    @Override
    public Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException {
        Optional<Reservation> requestFound = query().filter(r -> r.getRequest().equals(requestSearched)).findFirst();

        if (!requestFound.isPresent())
            throw new ReservationNotFoundException();
        return (requestFound.get());
    }
}
