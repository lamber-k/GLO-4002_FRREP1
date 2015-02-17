package org.Marv1n.code;

import org.Marv1n.code.Reservable.ExceptionReservableAlreadyBooked;
import org.Marv1n.code.Reservable.ExceptionReservableInsufficientCapacity;
import org.Marv1n.code.Reservable.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    final static private Integer NUMBER_OF_SEATS = 25;
    final static private Integer LOWER_NUMBER_OF_SEATS = 5;
    final static private Integer HIGHER_NUMBER_OF_SEATS = 35;

    private Room room;

    @Mock
    private Request request;

    @Before
    public void initializeNewRoom() {
        this.room = new Room(NUMBER_OF_SEATS);
    }

    @Test
    public void newRoom_AtStart_IsNotReserved() {
        assertFalse(this.room.isBooked());
    }

    @Test
    public void newRoom_WhenReserve_IsReserved() throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity {
        this.room.book(this.request);
        assertTrue(this.room.isBooked());
    }

    @Test
    public void newRoom_ReturnsCorrectNumberOfSeats() {
        Integer seats = this.room.getNumberSeats();
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
}
