package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    final static private int NUMBER_OF_SEATS = 25;
    final static private int LOWER_NUMBER_OF_SEATS = 5;
    private Room room;

    @Mock
    private Request request;

    @Before
    public void initializeNewRoom() {
        this.room = new Room(NUMBER_OF_SEATS);
    }

    @Test
    public void newRoom_AtStart_IsNotReserved() {
        assertFalse(this.room.isBooked());
    }

    @Test
    public void newRoom_WhenReserve_IsReserved() {
        this.room.book(this.request);
        assertTrue(this.room.isBooked());
    }

    @Test
    public void newRoom_WhenReserve_HaveTheRightReservation() {
        this.room.book(this.request);
        assertEquals(this.request, this.room.getRequest());
    }

    @Test
    public void newRoom_ReturnsCorrectNumberOfSeats() {
        int seats = this.room.getNumberSeats();
        assertEquals(NUMBER_OF_SEATS, seats);
    }

    @Test
    public void twoRooms_WhenTestRoomWithLowerSeatsCapacity_ShouldReturnTrue() throws Exception {
        Room greaterRoom = new Room(NUMBER_OF_SEATS);
        Room lowerRoom = new Room(LOWER_NUMBER_OF_SEATS);
        assertTrue(greaterRoom.hasGreaterCapacityThan(lowerRoom));
    }
}
