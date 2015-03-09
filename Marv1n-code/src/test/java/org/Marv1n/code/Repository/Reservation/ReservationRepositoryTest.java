package org.Marv1n.code.Repository.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationRepositoryTest {

    private ReservationRepository reservations;
    @Mock
    private Reservation aReservation;
    @Mock
    private IReservable aReservable;
    @Mock
    private Request aRequest;

    @Before
    public void initializeReservationRepository() {
        reservations = new ReservationRepository();
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldExist() {
        reservations.create(aReservation);

        assertTrue(reservations.reservationExist(aReservation));
    }

    @Test
    public void givenEmptyReservation_whenTestReservationExist_shallNotExist() {
        assertFalse(reservations.reservationExist(aReservation));
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldRetrieveByReservable() throws ReservationNotFoundException {
        when(aReservation.getReserved()).thenReturn(aReservable);

        reservations.create(aReservation);

        assertEquals(aReservation, reservations.findReservationByReservable(aReservable));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_whenTryToFindReservationByReservable_thenShouldThrow() throws ReservationNotFoundException {
        IReservable anOtherReservable = mock(IReservable.class);
        when(aReservation.getReserved()).thenReturn(anOtherReservable);
        reservations.create(aReservation);

        reservations.findReservationByReservable(aReservable);
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

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_whenTryToFindReservationByRequest_thenShouldThrow() throws ReservationNotFoundException {
        Request anOtherRequest = mock(Request.class);
        when(aReservation.getRequest()).thenReturn(anOtherRequest);
        reservations.create(aReservation);

        reservations.findReservationByRequest(aRequest);
    }
}
