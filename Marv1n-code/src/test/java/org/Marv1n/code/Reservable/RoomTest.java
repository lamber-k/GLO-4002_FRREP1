package org.Marv1n.code.Reservable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    private static final int A_NUMBER_OF_SEATS = 25;
    private static final int A_NUMBER_OF_SEATS_DIFFERENT = A_NUMBER_OF_SEATS + 1;
    private static final int A_LOWER_NUMBER_OF_SEATS = 5;
    private static final int A_HIGHER_NUMBER_OF_SEATS = 35;
    private static final String A_ROOM_NAME = "The room name";
    private Room room;

    @Before
    public void initializeRoom() {
        room = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
    }

    @Test
    public void givenRoomWithXNumberOfSeats_WhenGetNumberOfSeats_ThenReturnCorrectNumberOfSeats() {
        int seats = room.getNumberOfSeats();
        assertEquals(A_NUMBER_OF_SEATS, seats);
    }

    @Test
    public void givenRoomWithAName_WhenGetName_ThenReturnCorrectName() {
        String name = room.getName();
        assertEquals(A_ROOM_NAME, name);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithGreaterCapacityThanLower_ThenShouldReturnTrue() {
        Room greaterRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room lowerRoom = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        boolean greaterRoomHasGreaterCapacity = greaterRoom.hasGreaterOrEqualCapacityThan(lowerRoom);

        assertTrue(greaterRoomHasGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithLowerCapacityThanGreater_ThenShouldReturnFalse() {
        Room lowerRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room greaterRoom = new Room(A_HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME);

        boolean lowerRoomHasNotGreaterCapacity = lowerRoom.hasGreaterOrEqualCapacityThan(greaterRoom);

        assertFalse(lowerRoomHasNotGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenComparedBetweenThem_ThenShouldReturnTrue() {
        Room sameRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        boolean roomHasEqualCapacity = room.hasGreaterOrEqualCapacityThan(sameRoom);
        assertTrue(roomHasEqualCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithAnotherSameSeatsCapacity_ThenShouldReturnZero() {
        Room sameRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        int sameCapacity = room.compareReservableCapacity(sameRoom);
        assertEquals(0, sameCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithDifferentSeatsCapacity_ThenShouldReturnTheDifference() {
        Room roomWithDifferentCapacity = new Room(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME);
        int capacity = room.compareReservableCapacity(roomWithDifferentCapacity);
        assertEquals(A_NUMBER_OF_SEATS - A_NUMBER_OF_SEATS_DIFFERENT, capacity);
    }

    @Test
    public void givenRoom_WhenComparedRoomWithHigherCapacity_ThenShouldReturnFalse() {
        assertFalse(room.hasEnoughCapacity(A_HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithLowerCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(A_LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithSameCapacity_ThenShouldReturnTrue() {
        assertTrue(room.hasEnoughCapacity(A_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedWithNullObject_ThenShouldReturnFalse() {
        assertFalse(room.equals(null));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentRoom_ThenShouldReturnFalse() {
        Room aDifferentRoom = new Room(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME);
        assertFalse(room.equals(aDifferentRoom));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = 0;
        assertFalse(room.equals(aDifferentObject));
    }
}
