package org.Marv1n.code;

import org.Marv1n.code.Repository.ReservationRepository;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ReservationRepositoryTest {
    private ReservationRepository   reservations;
    private Reservation aReservation;

    @Before
    public void initializeReservationRepository() {
        aReservation = mock(Reservation.class);
        reservations = new ReservationRepository();
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldExist() {
        reservations.create(aReservation);

        assertTrue(reservations.reservationExist(aReservation));
    }

    @Test
    public void givenEmptyReservation_whenTestReservationExist_shouldDoesntExist() {
        assertFalse(reservations.reservationExist(aReservation));
    }
}
