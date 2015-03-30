package org.Marv1n.core.room;

import org.Marv1n.core.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.InsufficientResourcesException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    private static final int A_NUMBER_OF_SEATS = 25;
    private static final int A_NUMBER_OF_SEATS_DIFFERENT = A_NUMBER_OF_SEATS + 1;
    private static final int A_LOWER_NUMBER_OF_SEATS = 5;
    private static final int A_HIGHER_NUMBER_OF_SEATS = 35;
    private static final String A_ROOM_NAME = "The room name";
    @Mock
    private Request requestMock;
    private Room room;

    @Before
    public void initializeRoom() {
        room = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
    }

    @Test
    public void givenANewRoom_WhenCheckIfReserved_ThenShouldNotBeReserved() {
        assertFalse(room.isReserved());
    }

    @Test
    public void givenNotReservedRoom_WhenReserveWithEnoughCapacity_ThenShouldReserveIt() throws RoomIsAlreadyReserved, RoomInsufficientSeats {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_LOWER_NUMBER_OF_SEATS);

        room.reserve(requestMock);

        assertTrue(room.isReserved());
    }

    @Test(expected = RoomInsufficientSeats.class)
    public void givenNotReservedRoom_WhenReserveWithNotEnoughCapacity_ThenShouldThrowInsufficientSeats() throws RoomIsAlreadyReserved, RoomInsufficientSeats {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_HIGHER_NUMBER_OF_SEATS);
        room.reserve(requestMock);
    }

    @Test (expected = RoomIsAlreadyReserved.class)
    public void givenReservedRoom_WhenReservedAgain_ThenThrowRoomIsAlreadyReserved() throws RoomIsAlreadyReserved, RoomInsufficientSeats {
        room.reserve(requestMock);
        room.reserve(requestMock);
    }

    @Test
    public void givenReservedRoom_WhenCancelReservation_ThenShouldNotBeReservedAnymore() throws RoomIsAlreadyReserved, RoomInsufficientSeats {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_LOWER_NUMBER_OF_SEATS);
        room.reserve(requestMock);

        room.cancelReservation();

        assertFalse(room.isReserved());
    }

    @Test
    public void givenRoomWithAName_WhenGetName_ThenReturnCorrectName() {
        String name = room.getName();
        assertEquals(A_ROOM_NAME, name);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenTestBestFitWithGreaterSeatsCapacity_ThenShouldReturnExpectedRoom() throws RoomInsufficientSeats {
        Room greaterMeetingRoom = new Room(A_HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(greaterMeetingRoom, A_LOWER_NUMBER_OF_SEATS);

        assertEquals(room, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenTestBestFitWithBetterSeatsCapacity_ThenShouldReturnExpectedRoom() throws RoomInsufficientSeats {
        Room lowerMeetingRoom = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(lowerMeetingRoom, A_LOWER_NUMBER_OF_SEATS);

        assertEquals(lowerMeetingRoom, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenTestBestFit_ThenShouldReturnThis() throws RoomInsufficientSeats {
        Room sameMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(sameMeetingRoom, A_NUMBER_OF_SEATS);

        assertEquals(room, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenThisHasNotEnoughCapacity_ThenShouldReturnAnother() throws RoomInsufficientSeats {
        Room roomWithEnoughSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room roomWithoutEnoughSeats = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = roomWithoutEnoughSeats.getBestFit(roomWithEnoughSeats, A_NUMBER_OF_SEATS);

        assertEquals(roomWithEnoughSeats, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenAnotherHasNotEnoughCapacity_ThenShouldReturnThis() throws RoomInsufficientSeats {
        Room roomWithEnoughSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room roomWithoutEnoughSeats = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = roomWithEnoughSeats.getBestFit(roomWithoutEnoughSeats, A_NUMBER_OF_SEATS);

        assertEquals(roomWithEnoughSeats, bestFitRoomResult);
    }

    @Test(expected = RoomInsufficientSeats.class)
    public void givenTwoRooms_WhenTestBestFitWithLowerSeatsThanNeeded_ThenShouldThrowInsufficientSeats() throws RoomInsufficientSeats {
        Room insufficientSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        room.getBestFit(insufficientSeats, A_HIGHER_NUMBER_OF_SEATS);
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
        Room aDifferentMeetingRoom = new Room(A_NUMBER_OF_SEATS_DIFFERENT, A_ROOM_NAME);
        assertFalse(room.equals(aDifferentMeetingRoom));
    }

    @Test
    public void givenRoom_WhenComparedWithDifferentObject_ThenShouldReturnFalse() {
        Integer aDifferentObject = 0;
        assertFalse(room.equals(aDifferentObject));
    }
}
