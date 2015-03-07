package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationRepositoryTest {
    private ReservationRepository   reservations;
    private Reservation aReservation;
    private IReservable aReservable;
    private Request aRequest;

    @Before
    public void initializeReservationRepository() {
        aReservation = mock(Reservation.class);
        aReservable = mock(IReservable.class);
        aRequest = mock(Request.class);
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

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldRetrieveByReservable() throws ReservationNotFoundException {
        when(aReservation.getReserved()).thenReturn(aReservable);

        reservations.create(aReservation);

        assertEquals(aReservation, reservations.findReservationByReservable(aReservable));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_whenFindByReservable_shouldThrow() throws ReservationNotFoundException {
        reservations.findReservationByReservable(aReservable);
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldRetrieveByRequest() throws ReservationNotFoundException {
        when(aReservation.getRequest()).thenReturn(aRequest);

        reservations.create(aReservation);

        assertEquals(aReservation, reservations.findReservationByRequest(aRequest));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_whenFindByRequest_shouldThrow() throws ReservationNotFoundException {
        reservations.findReservationByRequest(aRequest);
    }
}
