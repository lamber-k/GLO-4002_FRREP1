package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;
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
    final static private int HIGHER_NUMBER_OF_SEATS = 35;

    private Room room;

    @Mock
    private Request request;

    @Before
    public void initializeNewRoom() {
        this.room = new Room(NUMBER_OF_SEATS);
    }

    @Test
    public void newRoom_ReturnsCorrectNumberOfSeats() {
        int seats = this.room.getNumberSeats();
        assertEquals(NUMBER_OF_SEATS, seats);
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherLowerSeatsCapacityRoom_ShouldReturnTrue() {
        Room greaterRoom = new Room(NUMBER_OF_SEATS);
        Room lowerRoom = new Room(LOWER_NUMBER_OF_SEATS);
        assertTrue(greaterRoom.hasGreaterCapacityThan(lowerRoom));
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherHigherSeatsCapacityRoom_ShouldReturnFalse() {
        Room lowerRoom = new Room(NUMBER_OF_SEATS);
        Room greaterRoom = new Room(HIGHER_NUMBER_OF_SEATS);
        assertFalse(lowerRoom.hasGreaterCapacityThan(greaterRoom));
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherSameSeatsCapacityRoom_ShouldReturnTrue() {
        assertTrue(this.room.hasGreaterCapacityThan(this.room));
    }

    @Test
    public void twoRooms_WhenCompareRoomWithAnotherSameSeatsCapacityRoom_ShouldReturnZero() {
        assertEquals(0, this.room.compareReservableCapacity(this.room));
    }

    @Test
    public void newRoom_WhenTestRoomWithHigherCapacity_ShouldReturnFalse() {
        assertFalse(this.room.hasEnoughCapacity(HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithLowerCapacity_ShouldReturnTrue() {
        assertTrue(this.room.hasEnoughCapacity(LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithSameCapacity_ShouldReturnTrue() {
        assertTrue(this.room.hasEnoughCapacity(NUMBER_OF_SEATS));
    }

}
