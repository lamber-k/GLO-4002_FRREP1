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
        this.reservations.create(this.aReservation);

        assertTrue(this.reservations.reservationExist(this.aReservation));
    }

    @Test
    public void givenEmptyReservation_whenTestReservationExist_shallNotExist() {
        assertFalse(this.reservations.reservationExist(this.aReservation));
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldRetrieveByReservable() throws ReservationNotFoundException {
        when(this.aReservation.getReserved()).thenReturn(this.aReservable);

        this.reservations.create(aReservation);

        assertEquals(this.aReservation, this.reservations.findReservationByReservable(this.aReservable));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_whenTryToFindResercationByReservable_thenShouldThrow() throws ReservationNotFoundException {
        IReservable anOtherReservable = mock(IReservable.class);
        when(this.aReservation.getReserved()).thenReturn(anOtherReservable);
        this.reservations.create(this.aReservation);

        this.reservations.findReservationByReservable(this.aReservable);
    }


    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_whenFindByReservable_shouldThrow() throws ReservationNotFoundException {
        this.reservations.findReservationByReservable(this.aReservable);
    }

    @Test
    public void givenEmptyReservation_whenAddReservation_shouldRetrieveByRequest() throws ReservationNotFoundException {
        when(this.aReservation.getRequest()).thenReturn(this.aRequest);

        this.reservations.create(this.aReservation);

        assertEquals(this.aReservation, this.reservations.findReservationByRequest(this.aRequest));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_whenFindByRequest_shouldThrow() throws ReservationNotFoundException {
        this.reservations.findReservationByRequest(this.aRequest);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_whenTryToFindResercationByRequest_thenShouldThrow() throws ReservationNotFoundException {
        Request anOtherRequest = mock(Request.class);
        when(this.aReservation.getRequest()).thenReturn(anOtherRequest);
        this.reservations.create(this.aReservation);

        this.reservations.findReservationByRequest(this.aRequest);
    }

}
