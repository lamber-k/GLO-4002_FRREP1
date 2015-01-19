package org.Marv1n.test;

import org.Marv1n.code.Room;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by maxime on 14/01/2015.
 */
public class RoomTest {
    @Test
    public void aNewRoomIsNotAvailable() {
        Room room = new Room();

        assertFalse(room.isAvailable());
    }
}
