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
    private Reservation reservationMock;
    @Mock
    private IReservable reservableMock;
    @Mock
    private Request requestMock;

    @Before
    public void initializeReservationRepository() {
        reservations = new ReservationRepository();
    }

    @Test
    public void givenEmptyReservation_WhenAddReservation_ThenShouldExist() {
        reservations.create(reservationMock);

        assertTrue(reservations.reservationExist(reservationMock));
    }

    @Test
    public void givenEmptyReservation_WhenTestReservationExist_ThenShallNotExist() {
        assertFalse(reservations.reservationExist(reservationMock));
    }

    @Test
    public void givenEmptyReservation_WhenAddReservation_ThenShouldRetrieveByReservable() throws ReservationNotFoundException {
        when(reservationMock.getReserved()).thenReturn(reservableMock);

        reservations.create(reservationMock);

        assertEquals(reservationMock, reservations.findReservationByReservable(reservableMock));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_WhenTryToFindReservationByReservable_ThenShouldThrow() throws ReservationNotFoundException {
        IReservable anOtherReservable = mock(IReservable.class);
        when(reservationMock.getReserved()).thenReturn(anOtherReservable);
        reservations.create(reservationMock);

        reservations.findReservationByReservable(reservableMock);
    }


    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_WhenFindByReservable_ThenShouldThrow() throws ReservationNotFoundException {
        reservations.findReservationByReservable(reservableMock);
    }

    @Test
    public void givenEmptyReservation_WhenAddReservation_ThenShouldRetrieveByRequest() throws ReservationNotFoundException {
        when(reservationMock.getRequest()).thenReturn(requestMock);

        reservations.create(reservationMock);

        assertEquals(reservationMock, reservations.findReservationByRequest(requestMock));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_WhenFindByRequest_ThenShouldThrow() throws ReservationNotFoundException {
        reservations.findReservationByRequest(requestMock);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNonEmptyReservation_WhenTryToFindReservationByRequest_ThenShouldThrow() throws ReservationNotFoundException {
        Request anOtherRequest = mock(Request.class);
        when(reservationMock.getRequest()).thenReturn(anOtherRequest);
        reservations.create(reservationMock);

        reservations.findReservationByRequest(requestMock);
    }
}
