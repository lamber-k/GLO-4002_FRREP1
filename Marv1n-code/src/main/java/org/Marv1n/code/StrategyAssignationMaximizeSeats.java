package org.Marv1n.code;

import java.util.Iterator;
import java.util.List;

public class StrategyAssignationMaximizeSeats extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(List<Room> rooms, Request evaluatedRequest) {
        Room betterRoom = null;

        for (Room room : rooms) {
            if (doesTheRoomFitsTheRequest(evaluatedRequest, room)) {
                betterRoom = getBetterRoomOf(betterRoom, room);
            }
        }
        return new RoomAssignationResult(betterRoom);
    }

    @Override
    protected void treatAssignationResult(AssignationResult result, Request evaluatedRequest) {
        RoomAssignationResult roomAssignationResult = (RoomAssignationResult)result;
        roomAssignationResult.getBestRoomMatch().book(evaluatedRequest);
    }

    private Room getBetterRoomOf(Room bestRoom, Room room) {
        if (bestRoom == null || bestRoom.hasGreaterCapacityThan(room))
            return room;
        return bestRoom;
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean doesTheRoomFitsTheRequest(Request evaluatedRequest, Room room) {
        return !(room.isBooked() || evaluatedRequest.getNumberOdSeatsNeeded() > room.getNumberSeats());
    }
}
