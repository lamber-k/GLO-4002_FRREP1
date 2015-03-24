package Persistence;

import Persistence.ReservationRepositoryInMemory;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Reservation.ReservationNotFoundException;
import org.Marv1n.core.Room.Room;
import org.Marv1n.core.Reservation.Reservation;
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
    private Room roomMock;
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
        when(reservationMock.getReserved()).thenReturn(roomMock);
        reservations.create(reservationMock);

        assertEquals(reservationMock, reservations.findReservationByReservable(roomMock));
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenNotEmptyReservation_WhenTryToFindReservationByReservable_ThenShouldThrow() throws ReservationNotFoundException {
        Room anotherRoomMock = mock(Room.class);
        when(reservationMock.getReserved()).thenReturn(anotherRoomMock);
        reservations.create(reservationMock);

        reservations.findReservationByReservable(roomMock);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenEmptyReservation_WhenFindByReservable_ThenShouldThrow() throws ReservationNotFoundException {
        reservations.findReservationByReservable(roomMock);
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
