package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

    final static private int NUMBER_OF_SEATS = 25;
    private Room room;
    private Request request;

    @Before
    public void initializeNewRoom() {
        this.room = new Room(NUMBER_OF_SEATS);
        this.request = new Request();
    }

    @Test
    public void newRoomIsNotReserved() {
        assertFalse(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveIsReserved() {
        this.room.book(request);
        assertTrue(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveHaveTheRightReservation() {
        this.room.book(request);
        assertEquals(request, this.room.getRequest());
    }

    @Test
    public void newRoomReturnsCorrectNumberOfSeats() {
        int seats = room.getNumberSeats();
        assertEquals(NUMBER_OF_SEATS, seats);
    }
}
