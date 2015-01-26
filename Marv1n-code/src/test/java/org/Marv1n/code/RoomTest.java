package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rafaël on 21/01/2015.
 */
public class RoomTest {

    private Room room;

    @Before
    public void createARoom() {
        this.room = new Room();
    }

    @Test
    public void NewRoomIsNotReserved() {
        assertFalse(this.room.IsBooked());
    }

    @Test
    public void NewRoomWhenReserveIsReserved() {
        this.room.Book(new Request());
        assertTrue(this.room.IsBooked());
    }

    @Test
    public void NewRoomWhenReserveHaveTheRightReservation() {
        Request test = new Request();
        this.room.Book(test);
        assertEquals(test, this.room.GetRequest());
    }
}
