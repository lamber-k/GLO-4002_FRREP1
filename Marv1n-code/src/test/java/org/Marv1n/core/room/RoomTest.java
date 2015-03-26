package org.Marv1n.core.room;

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
    private static final boolean NOT_RESERVED = false;
    private Room room;

    @Before
    public void initializeRoom() {
        room = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);
    }

    @Test
    public void givenNotReservedRoom_WhenReserved_ThenTheRoomIsReserved() throws RoomIsAlreadyReserved {
        room.reserve();
    }

    @Test (expected = RoomIsAlreadyReserved.class)
    public void givenReservedRoom_WhenReservedAgain_ThenThrowRoomIsAlreadyReserved() throws RoomIsAlreadyReserved {
        room.reserve();
        room.reserve();
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
        Room greaterMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);
        Room lowerMeetingRoom = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);

        boolean greaterRoomHasGreaterCapacity = greaterMeetingRoom.hasGreaterOrEqualCapacityThan(lowerMeetingRoom);

        assertTrue(greaterRoomHasGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithLowerCapacityThanGreater_ThenShouldReturnFalse() {
        Room lowerMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);
        Room greaterMeetingRoom = new Room(A_HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);

        boolean lowerRoomHasNotGreaterCapacity = lowerMeetingRoom.hasGreaterOrEqualCapacityThan(greaterMeetingRoom);

        assertFalse(lowerRoomHasNotGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenComparedBetweenThem_ThenShouldReturnTrue() {
        Room sameMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);
        boolean roomHasEqualCapacity = room.hasGreaterOrEqualCapacityThan(sameMeetingRoom);
        assertTrue(roomHasEqualCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithAnotherSameSeatsCapacity_ThenShouldReturnZero() {
        Room sameMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME, NOT_RESERVED);
        int sameCapacity = room.compareReservableCapacity(sameMeetingRoom);
        assertEquals(0, sameCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithDifferentSeatsCapacity_ThenShouldReturnTheDifference() {
        Room meetingRoomWithDifferentCapacity = new Room(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME, NOT_RESERVED);
        int capacity = room.compareReservableCapacity(meetingRoomWithDifferentCapacity);
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
        Room aDifferentMeetingRoom = new Room(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME, NOT_RESERVED);
        assertFalse(room.equals(aDifferentMeetingRoom));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = 0;
        assertFalse(room.equals(aDifferentObject));
    }
}
