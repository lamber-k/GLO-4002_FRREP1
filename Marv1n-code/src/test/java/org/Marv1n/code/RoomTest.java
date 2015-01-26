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
        assertFalse(this.room.IsReserved());
    }

    @Test
    public void NewRoomWhenReserveIsReserved() {
        this.room.Reserve(new Reservation());
        assertTrue(this.room.IsReserved());
    }

    @Test
    public void NewRoomWhenReserveHaveTheRightReservation() {
        Reservation test = new Reservation();
        this.room.Reserve(test);
        assertEquals(test, this.room.GetReservation());
    }
}
