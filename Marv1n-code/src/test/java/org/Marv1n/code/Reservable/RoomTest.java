package org.Marv1n.code.Reservable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    final static private int NUMBER_OF_SEATS = 25;
    final static private int NUMBER_OF_SEATS_DIFFERENT = NUMBER_OF_SEATS + 1;
    final static private int LOWER_NUMBER_OF_SEATS = 5;
    final static private int HIGHER_NUMBER_OF_SEATS = 35;
    final static private String ROOM_NAME = "The room name";
    private Room room;

    @Before
    public void initializeRoom() {
        room = new Room(NUMBER_OF_SEATS, ROOM_NAME);
    }

    @Test
    public void givenRoomWithXNumberOfSeats_WhenGetNumberOfSeats_ThenReturnCorrectNumberOfSeats() {
        int seats = room.getNumberOfSeats();
        assertEquals(NUMBER_OF_SEATS, seats);
    }

    @Test
    public void givenRoomWithAName_WhenGetName_ThenReturnCorrectName() {
        String name = room.getName();
        assertEquals(ROOM_NAME, name);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithGreaterCapacityThanLower_ThenShouldReturnTrue() {
        Room greaterRoom = new Room(NUMBER_OF_SEATS, ROOM_NAME);
        Room lowerRoom = new Room(LOWER_NUMBER_OF_SEATS, ROOM_NAME);

        boolean greaterRoomHasGreaterCapacity = greaterRoom.hasGreaterOrEqualCapacityThan(lowerRoom);

        assertTrue(greaterRoomHasGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithLowerCapacityThanGreater_ThenShouldReturnFalse() {
        Room lowerRoom = new Room(NUMBER_OF_SEATS, ROOM_NAME);
        Room greaterRoom = new Room(HIGHER_NUMBER_OF_SEATS, ROOM_NAME);

        boolean lowerRoomHasNotGreaterCapacity = lowerRoom.hasGreaterOrEqualCapacityThan(greaterRoom);

        assertFalse(lowerRoomHasNotGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenComparedBetweenThem_ThenShouldReturnTrue() {
        Room sameRoom = new Room(NUMBER_OF_SEATS, ROOM_NAME);
        boolean roomHasEqualCapacity = room.hasGreaterOrEqualCapacityThan(sameRoom);
        assertTrue(roomHasEqualCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithAnotherSameSeatsCapacity_ThenShouldReturnZero() {
        Room sameRoom = new Room(NUMBER_OF_SEATS, ROOM_NAME);
        int sameCapacity = room.compareReservableCapacity(sameRoom);
        assertEquals(0, sameCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithDifferentSeatsCapacity_ThenShouldReturnTheDifference() {
        Room roomWithDifferentCapacity = new Room(NUMBER_OF_SEATS_DIFFERENT , ROOM_NAME);
        int capacity = room.compareReservableCapacity(roomWithDifferentCapacity);
        assertEquals(NUMBER_OF_SEATS - NUMBER_OF_SEATS_DIFFERENT, capacity);
    }

    @Test
    public void givenRoom_WhenComparedRoomWithHigherCapacity_ThenShouldReturnFalse() {
        assertFalse(room.hasEnoughCapacity(HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithLowerCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithSameCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedWithNullObject_ThenShouldReturnFalse() {
        assertFalse(room.equals(null));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentRoom_ThenShouldReturnFalse() {
        Room aDifferentRoom = new Room(NUMBER_OF_SEATS_DIFFERENT, ROOM_NAME);
        assertFalse(room.equals(aDifferentRoom));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = Integer.valueOf(0);
        assertFalse(room.equals(aDifferentObject));
    }
}
