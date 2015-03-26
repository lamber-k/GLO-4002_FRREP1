package org.Marv1n.core.room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MeetingRoomTest {

    private static final int A_NUMBER_OF_SEATS = 25;
    private static final int A_NUMBER_OF_SEATS_DIFFERENT = A_NUMBER_OF_SEATS + 1;
    private static final int A_LOWER_NUMBER_OF_SEATS = 5;
    private static final int A_HIGHER_NUMBER_OF_SEATS = 35;
    private static final String A_ROOM_NAME = "The room name";
    private MeetingRoom meetingRoom;

    @Before
    public void initializeRoom() {
        meetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS, A_ROOM_NAME);
    }

    @Test
    public void givenRoomWithXNumberOfSeats_WhenGetNumberOfSeats_ThenReturnCorrectNumberOfSeats() {
        int seats = meetingRoom.getNumberOfSeats();
        assertEquals(A_NUMBER_OF_SEATS, seats);
    }

    @Test
    public void givenRoomWithAName_WhenGetName_ThenReturnCorrectName() {
        String name = meetingRoom.getName();
        assertEquals(A_ROOM_NAME, name);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithGreaterCapacityThanLower_ThenShouldReturnTrue() {
        MeetingRoom greaterMeetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        MeetingRoom lowerMeetingRoom = new MeetingRoom(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        boolean greaterRoomHasGreaterCapacity = greaterMeetingRoom.hasGreaterOrEqualCapacityThan(lowerMeetingRoom);

        assertTrue(greaterRoomHasGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenComparedRoomWithLowerCapacityThanGreater_ThenShouldReturnFalse() {
        MeetingRoom lowerMeetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        MeetingRoom greaterMeetingRoom = new MeetingRoom(A_HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME);

        boolean lowerRoomHasNotGreaterCapacity = lowerMeetingRoom.hasGreaterOrEqualCapacityThan(greaterMeetingRoom);

        assertFalse(lowerRoomHasNotGreaterCapacity);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenComparedBetweenThem_ThenShouldReturnTrue() {
        MeetingRoom sameMeetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        boolean roomHasEqualCapacity = meetingRoom.hasGreaterOrEqualCapacityThan(sameMeetingRoom);
        assertTrue(roomHasEqualCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithAnotherSameSeatsCapacity_ThenShouldReturnZero() {
        MeetingRoom sameMeetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        int sameCapacity = meetingRoom.compareReservableCapacity(sameMeetingRoom);
        assertEquals(0, sameCapacity);
    }

    @Test
    public void givenTwoRooms_WhenComparedRoomWithDifferentSeatsCapacity_ThenShouldReturnTheDifference() {
        MeetingRoom meetingRoomWithDifferentCapacity = new MeetingRoom(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME);
        int capacity = meetingRoom.compareReservableCapacity(meetingRoomWithDifferentCapacity);
        assertEquals(A_NUMBER_OF_SEATS - A_NUMBER_OF_SEATS_DIFFERENT, capacity);
    }

    @Test
    public void givenRoom_WhenComparedRoomWithHigherCapacity_ThenShouldReturnFalse() {
        assertFalse(meetingRoom.hasEnoughCapacity(A_HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithLowerCapacity_ThenShouldReturnTrue() {
        assertTrue(meetingRoom.hasEnoughCapacity(A_LOWER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedRoomWithSameCapacity_ThenShouldReturnTrue() {
        assertTrue(meetingRoom.hasEnoughCapacity(A_NUMBER_OF_SEATS));
    }

    @Test
    public void givenRoom_WhenComparedWithNullObject_ThenShouldReturnFalse() {
        assertFalse(meetingRoom.equals(null));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentRoom_ThenShouldReturnFalse() {
        MeetingRoom aDifferentMeetingRoom = new MeetingRoom(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME);
        assertFalse(meetingRoom.equals(aDifferentMeetingRoom));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = 0;
        assertFalse(meetingRoom.equals(aDifferentObject));
    }
}
