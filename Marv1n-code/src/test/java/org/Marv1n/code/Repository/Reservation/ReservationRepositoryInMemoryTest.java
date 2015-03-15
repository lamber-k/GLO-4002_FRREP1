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
public class ReservationRepositoryInMemoryTest {

    private ReservationRepositoryInMemory reservations;
    @Mock
    private Reservation reservationMock;
    @Mock
    private IReservable reservableMock;
    @Mock
    private Request requestMock;

    @Before
    public void initializeReservationRepositoryInMemory() {
        reservations = new ReservationRepositoryInMemory();
    }

    @Test
    public void givenEmptyReservation_WhenAddReservation_ThenShouldExist() {
        reservations.create(reservationMock);
        boolean reservationExist = reservations.reservationExist(reservationMock);
        assertTrue(reservationExist);
    }

    @Test
    public void givenEmptyReservation_WhenTestReservationExist_ThenShallNotExist() {
        boolean reservationNotExist = reservations.reservationExist(reservationMock);
        assertFalse(reservationNotExist);
    }

    @Test
    public void givenEmptyReservation_WhenAddReservation_ThenShouldRetrieveByReservable() throws ReservationNotFoundException {
        when(reservationMock.getReserved()).thenReturn(reservableMock);
        reservations.create(reservationMock);

        assertEquals(reservationMock, reservations.findReservationByReservable(reservableMock));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNotEmptyReservation_WhenTryToFindReservationByReservable_ThenShouldThrow() throws ReservationNotFoundException {
        IReservable anotherReservableMock = mock(IReservable.class);
        when(reservationMock.getReserved()).thenReturn(anotherReservableMock);
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
        Request anotherRequestMock = mock(Request.class);
        when(reservationMock.getRequest()).thenReturn(anotherRequestMock);
        reservations.create(reservationMock);

        reservations.findReservationByRequest(requestMock);
    }
}
