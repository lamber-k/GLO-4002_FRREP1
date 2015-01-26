package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class RoomTest {

    private Room room;

    @Before
    public void initializeNewRoom() {
        this.room = new Room();
    }

    @Test
    public void newRoomIsNotReserved() {
        assertFalse(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveIsReserved() {
        this.room.book(new Request());
        assertTrue(this.room.isBooked());
    }

    @Test
    public void newRoomWhenReserveHaveTheRightReservation() {
        Request test = new Request();
        this.room.book(test);
        assertEquals(test, this.room.getRequest());
    }
}
