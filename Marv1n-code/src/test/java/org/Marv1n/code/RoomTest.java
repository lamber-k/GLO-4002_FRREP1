package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class RoomTest {

    private Room room;
    private Request request;

    @Before
    public void initializeNewRoom() {
        this.room = new Room();
        this.request = new Request();
    }

    @Test
    public void newRoomIsNotReserved() {
        assertFalse(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveIsReserved() {
        this.room.book(this.request);
        assertTrue(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveHaveTheRightReservation() {
        this.room.book(this.request);
        assertEquals(this.request, this.room.getRequest());
    }
}
