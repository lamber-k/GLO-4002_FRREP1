package org.Marv1n.core.Reservation;

import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Room.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ReservationTest {

    private Reservation reservation;
    @Mock
    private Request requestMock;
    @Mock
    private Room roomMock;

    @Before
    public void initializeReservation() {
        reservation = new Reservation(requestMock, roomMock);
    }

    @Test
    public void givenNullObject_WhenCompared_ThenEqualsReturnFalse() {
        assertFalse(reservation.equals(null));
    }

    @Test
    public void givenDifferentObject_WhenCompared_ThenEqualsReturnFalse() {
        assertFalse(reservation.equals(requestMock));
    }

    @Test
    public void givenAnotherReservations_WhenCompared_ThenEqualsReturnFalse() {
        Request anotherRequestMock = mock(Request.class);
        Room anotherRoomMock = mock(Room.class);
        Reservation anotherReservation = new Reservation(anotherRequestMock, anotherRoomMock);

        assertFalse(reservation.equals(anotherReservation));
        assertNotEquals(reservation.hashCode(), anotherReservation.hashCode());
    }

    @Test
    public void givenSameObject_WhenCompared_ThenEqualsReturnTrue() {
        assertTrue(reservation.equals(reservation));
    }

    @Test
    public void givenTwoSameObject_WhenCompared_ThenEqualsReturnTrue() {
        Reservation sameReservation = new Reservation(requestMock, roomMock);
        assertTrue(reservation.equals(sameReservation));
    }
}