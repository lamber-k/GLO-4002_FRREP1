package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomAssignationResultTest {

    private static final Integer A_NUMBER_OF_SEAT = 4;

    private Room aRoom;

    @Before
    public void initializeNewRoom() {
        this.aRoom = new Room(A_NUMBER_OF_SEAT);
    }

    @Test
    public void newRoom_WhenAssignationResult_ShouldNotFoundMatch() {
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult();
        assertFalse(roomAssignationResult.matchFound());
    }

    @Test
    public void newRoom_WhenAssignationResultWithRoom_ShouldFoundMatch() {
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult(this.aRoom);
        assertTrue(roomAssignationResult.matchFound());
    }

    @Test
    public void newRoom_WhenAssignationResultWithRoom_CanReturnMatchingRoom() {
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult(this.aRoom);
        assertEquals(this.aRoom, roomAssignationResult.getBestRoomMatch());
    }
}
