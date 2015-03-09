package org.Marv1n.code.Reservation;

import org.Marv1n.code.Request;
import org.Marv1n.code.Reservable.IReservable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ReservationTest {

    @Mock
    private Request mockRequest;
    @Mock
    private IReservable mockReservable;
    private Reservation reservation;


    @Before
    public void init() {
        reservation = new Reservation(mockRequest, mockReservable);
    }

    @Test
    public void givenAReservation_WhenHashIsCalled_ThenExpectHash() {
        int expectedHash = (17 + mockRequest.hashCode()) * 13 + mockReservable.hashCode();

        assertEquals(expectedHash, reservation.hashCode());
    }

    @Test
    public void givenNullObject_WhenCompared_ThenEqualsReturnFalse() {
        assertFalse(reservation.equals(null));
    }

    @Test
    public void givenDifferentObject_WhenCompared_ThenEqualsReturnFalse() {
        assertFalse(reservation.equals(mockRequest));
    }

    @Test
    public void givenAnotherReservations_WhenCompared_ThenEqualsReturnFalse() {
        Request aOtherMockRequest = mock(Request.class);
        IReservable aOtherMockReservable = mock(IReservable.class);
        Reservation anOtherReservation = new Reservation(aOtherMockRequest, aOtherMockReservable);

        assertFalse(reservation.equals(anOtherReservation));
    }

    @Test
    public void givenSameObject_WhenCompared_ThenEqualsReturnTrue() {
        assertTrue(reservation.equals(reservation));
    }

}