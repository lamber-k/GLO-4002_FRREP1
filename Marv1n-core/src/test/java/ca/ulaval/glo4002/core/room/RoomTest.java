package ca.ulaval.glo4002.core.room;

import ca.ulaval.glo4002.core.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void givenReservedRoom_WhenReserve_ThenRequestIsAssociatedToRoom() throws RoomAlreadyReservedException {
        room.book(requestMock);
        assertEquals(requestMock, room.getRequest());
    }


    @Test(expected = RoomAlreadyReservedException.class)
    public void givenReservedRoom_WhenReservedAgain_ThenThrowRoomAlreadyReserved() throws RoomAlreadyReservedException {
        room.book(requestMock);
        room.book(requestMock);
    }

    @Test
    public void givenReservedRoom_WhenCancelReservation_ThenShouldNotBeReservedAnymore() throws RoomAlreadyReservedException {
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(A_LOWER_NUMBER_OF_SEATS);
        room.book(requestMock);

        room.unbook();

        assertFalse(room.isReserved());
    }

    @Test
    public void givenRoomWithAName_WhenGetName_ThenReturnCorrectName() {
        String name = room.getName();
        assertEquals(A_ROOM_NAME, name);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenTestBestFitWithGreaterSeatsCapacity_ThenShouldReturnExpectedRoom() {
        Room greaterMeetingRoom = new Room(A_HIGHER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(greaterMeetingRoom, A_LOWER_NUMBER_OF_SEATS);

        assertEquals(room, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenTestBestFitWithBetterSeatsCapacity_ThenShouldReturnExpectedRoom() {
        Room lowerMeetingRoom = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(lowerMeetingRoom, A_LOWER_NUMBER_OF_SEATS);

        assertEquals(lowerMeetingRoom, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithEqualNumberOfSeats_WhenTestBestFit_ThenShouldReturnThis() {
        Room sameMeetingRoom = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = room.getBestFit(sameMeetingRoom, A_NUMBER_OF_SEATS);

        assertEquals(room, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenThisHasNotEnoughCapacity_ThenShouldReturnAnother() {
        Room roomWithEnoughSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room roomWithoutEnoughSeats = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = roomWithoutEnoughSeats.getBestFit(roomWithEnoughSeats, A_NUMBER_OF_SEATS);

        assertEquals(roomWithEnoughSeats, bestFitRoomResult);
    }

    @Test
    public void givenTwoRoomsWithDifferentNumberOfSeats_WhenAnotherHasNotEnoughCapacity_ThenShouldReturnThis() {
        Room roomWithEnoughSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        Room roomWithoutEnoughSeats = new Room(A_LOWER_NUMBER_OF_SEATS, A_ROOM_NAME);

        Room bestFitRoomResult = roomWithEnoughSeats.getBestFit(roomWithoutEnoughSeats, A_NUMBER_OF_SEATS);

        assertEquals(roomWithEnoughSeats, bestFitRoomResult);
    }

    @Test
    public void givenTwoRooms_WhenTestBestFitWithLowerSeatsThanNeeded_ThenShouldReturnNull() {
        Room insufficientSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        assertNull(room.getBestFit(insufficientSeats, A_HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenOneRoomsWithNotEnoughSeats_WhenTestBestFitWithNull_ThenShouldReturnNull() {
        assertNull(room.getBestFit(null, A_HIGHER_NUMBER_OF_SEATS));
    }

    @Test
    public void givenOneRoomsWithEnoughSeats_WhenTestBestFitWithNull_ThenShouldThis() {
        Room roomWithEnoughSeats = new Room(A_NUMBER_OF_SEATS, A_ROOM_NAME);
        assertEquals(roomWithEnoughSeats, roomWithEnoughSeats.getBestFit(null, A_NUMBER_OF_SEATS));
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
        Object aDifferentObject = 0;
        assertFalse(room.equals(aDifferentObject));
    }
}
