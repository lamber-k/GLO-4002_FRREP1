package org.Marv1n.code;

import java.util.List;

public class StrategyAssignationFirstInFirstOut extends StrategyAssignationSequential {

    @Override
    protected AssignationResult evaluateOneRequest(List<Room> rooms, Request request) {
        for (Room room : rooms)
            if (!room.isBooked()) {
                return new RoomAssignationResult(room);
            }
        return new RoomAssignationResult();
    }

    @Override
    protected void treatAssignationResult(AssignationResult result, Request evaluatedRequest) {
        RoomAssignationResult roomAssignationResult = (RoomAssignationResult)result;
        roomAssignationResult.getBestRoomMatch().book(evaluatedRequest);
    }
}
