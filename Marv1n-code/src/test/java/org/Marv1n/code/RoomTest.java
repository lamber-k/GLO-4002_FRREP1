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

    @Test
    public void greaterCountRoomEvaluatedAsSuchWhenComparedToLowerRoom() throws Exception {
        Room greaterRoom = new Room(NUMBER_OF_SEATS);
        Room lowerRoom = new Room(LOWER_NUMBER_OF_SEATS);
        assertTrue(greaterRoom.hasGreaterCapacityThan(lowerRoom));
    }
}
