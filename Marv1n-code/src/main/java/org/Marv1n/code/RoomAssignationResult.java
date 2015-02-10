package org.Marv1n.code;

public class RoomAssignationResult implements AssignationResult {

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
