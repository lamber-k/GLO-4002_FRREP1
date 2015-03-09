package org.Marv1n.code.Reservable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    final static private int NUMBER_OF_SEATS = 25;
    final static private int LOWER_NUMBER_OF_SEATS = 5;
    final static private int HIGHER_NUMBER_OF_SEATS = 35;

    private Room room;

    @Before
    public void initializeNewRoom() {
        room = new Room(NUMBER_OF_SEATS);
    }

    @Test
    public void newRoom_ReturnsCorrectNumberOfSeats() {
        int seats = room.getNumberOfSeats();
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
        assertTrue(room.hasGreaterCapacityThan(room));
    }

    @Test
    public void twoRooms_WhenCompareRoomWithAnotherSameSeatsCapacityRoom_ShouldReturnZero() {
        assertEquals(0, room.compareReservableCapacity(room));
    }

    @Test
    public void newRoom_WhenTestRoomWithHigherCapacity_ShouldReturnFalse() {
        assertFalse(room.hasEnoughCapacity(HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithLowerCapacity_ShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithSameCapacity_ShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(NUMBER_OF_SEATS));
    }

    @Test
    public void aRoom_WhenComparedWithNullObject_ShouldReturnFalse() {
        assertFalse(room.equals(null));
    }

    @Test
    public void aRoom_WhenComparedWithDifferentRoom_ShouldReturnFalse() {
        Room aDifferentRoom = new Room(LOWER_NUMBER_OF_SEATS);
        assertFalse(room.equals(aDifferentRoom));
    }

    @Test
    public void aRoom_WhenComparedWithDifferentObject_ShouldReturnFalse() {
        Integer aDifferentObject = Integer.valueOf(0);
        assertFalse(room.equals(aDifferentObject));
    }
}
