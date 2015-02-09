package org.Marv1n.code;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rafaël on 09/02/2015.
 */
public class RoomAssignationResultTest {

    private static final int A_NUMBER_OF_SEAT = 4;

    @Test
    public void newRoomAssignationResult_ShouldNotFoundMatch() {
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult();
        assertFalse(roomAssignationResult.matchFound());
    }

    @Test
    public void newRoomAssignationResultWithRoom_ShouldFoundMatch() {
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult(new Room(A_NUMBER_OF_SEAT));
        assertTrue(roomAssignationResult.matchFound());
    }

    @Test
    public void newRoomAssignationResultWithRoom_CanReturnMatchingRoom() {
        Room aRoom = new Room(A_NUMBER_OF_SEAT);
        RoomAssignationResult roomAssignationResult = new RoomAssignationResult(aRoom);
        assertEquals(aRoom, roomAssignationResult.getBestRoomMatch());
    }
}
