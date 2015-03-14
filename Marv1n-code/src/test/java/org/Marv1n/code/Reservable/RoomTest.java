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
    final static private String A_ROOM_NAME = "The room name";
    private Room room;

    @Before
    public void initializeNewRoom() {
        room = new Room(NUMBER_OF_SEATS, A_ROOM_NAME);
    }

    @Test
    public void newRoomReturnsCorrectNumberOfSeats() {
        int seats = room.getNumberOfSeats();
        assertEquals(NUMBER_OF_SEATS, seats);
    }

    @Test
    public void newRoomReturnsCorrectName() {
        String name = room.getName();
        assertEquals(A_ROOM_NAME, name);
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherLowerSeatsCapacityRoom_ThenShouldReturnTrue() {
        Room greaterRoom = new Room(NUMBER_OF_SEATS, A_ROOM_NAME);
        Room lowerRoom = new Room(LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);
        assertTrue(greaterRoom.hasGreaterCapacityThan(lowerRoom));
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherHigherSeatsCapacityRoom_ThenShouldReturnFalse() {
        Room lowerRoom = new Room(NUMBER_OF_SEATS, A_ROOM_NAME);
        Room greaterRoom = new Room(HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME);
        assertFalse(lowerRoom.hasGreaterCapacityThan(greaterRoom));
    }

    @Test
    public void twoRooms_WhenTestRoomWithAnotherSameSeatsCapacityRoom_ThenShouldReturnTrue() {
        assertTrue(room.hasGreaterCapacityThan(room));
    }

    @Test
    public void twoRooms_WhenCompareRoomWithAnotherSameSeatsCapacityRoom_ThenShouldReturnZero() {
        assertEquals(0, room.compareReservableCapacity(room));
    }

    @Test
    public void newRoom_WhenTestRoomWithHigherCapacity_ThenShouldReturnFalse() {
        assertFalse(room.hasEnoughCapacity(HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithLowerCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void newRoom_WhenTestRoomWithSameCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(NUMBER_OF_SEATS));
    }

    @Test
    public void aRoom_WhenComparedWithNullObject_ThenShouldReturnFalse() {
        assertFalse(room.equals(null));
    }

    @Test
    public void aRoom_WhenComparedWithDifferentRoom_ThenShouldReturnFalse() {
        Room aDifferentRoom = new Room(LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);
        assertFalse(room.equals(aDifferentRoom));
    }

    @Test
    public void aRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = Integer.valueOf(0);
        assertFalse(room.equals(aDifferentObject));
    }
}
