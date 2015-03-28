package infrastructure.persistence;

import core.request.Request;
import core.reservation.Reservation;
import core.reservation.ReservationNotFoundException;
import core.persistence.ReservationRepository;
import core.room.Room;

import java.util.Optional;

public class ReservationRepositoryInMemory extends RepositoryInMemory<Reservation> implements ReservationRepository {

    @Override
    public boolean reservationExist(Reservation reservable) {
        return query().anyMatch(r -> r.equals(reservable));
    }

    @Override
    public Reservation findReservationByReservable(Room roomSearched) throws ReservationNotFoundException {
        Optional<Reservation> reservationFound = query().filter(r -> r.getReserved().equals(roomSearched)).findFirst();
        if (!reservationFound.isPresent()) {
            throw new ReservationNotFoundException();
        }
        return reservationFound.get();
    }

    @Override
    public Reservation findReservationByRequest(Request requestSearched) throws ReservationNotFoundException {
        Optional<Reservation> requestFound = query().filter(r -> r.getRequest().equals(requestSearched)).findFirst();
        if (!requestFound.isPresent()) {
            throw new ReservationNotFoundException();
        }
        return requestFound.get();
    }
}
