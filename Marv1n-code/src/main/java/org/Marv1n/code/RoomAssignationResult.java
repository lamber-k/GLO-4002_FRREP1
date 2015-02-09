package org.Marv1n.code;

/**
 * Created by Rafaël on 09/02/2015.
 */
public class RoomAssignationResult implements AssignationResult{

    private Room matchingRoom = null;

    public RoomAssignationResult(Room room) {
        this.matchingRoom = room;
    }

    public RoomAssignationResult() {
    }

    @Override
    public boolean matchFound() {
        return this.matchingRoom != null;
    }

    public Room getBestRoomMatch() {
        return this.matchingRoom;
    }
}
