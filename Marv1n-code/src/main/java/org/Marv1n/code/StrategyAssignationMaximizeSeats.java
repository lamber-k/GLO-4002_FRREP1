package org.Marv1n.code;

import java.util.Iterator;
import java.util.List;

public class StrategyAssignationMaximizeSeats implements StrategyAssignation {
    @Override
    public void assignRooms(List<Request> requests, List<Room> rooms) {
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request evaluatedRequest = iterator.next();
            Room bestRoom = evaluateOneRequest(rooms, evaluatedRequest);
            if (bestRoom != null) {
                bestRoom.book(evaluatedRequest);
                iterator.remove();
            }
        }
    }

    private Room evaluateOneRequest(List<Room> rooms, Request evaluatedRequest) {
        Room betterRoom = null;

        for (Room room : rooms) {
            if (doesTheRoomFitsTheRequest(evaluatedRequest, room)) {
                betterRoom = getBetterRoomOf(betterRoom, room);
            }
        }
        return betterRoom;
    }

    private Room getBetterRoomOf(Room bestRoom, Room room) {
        if (bestRoom == null)
            return room;
        else if (bestRoom.hasGreaterCapacityThan(room))
            return room;
        else
            return bestRoom;
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean doesTheRoomFitsTheRequest(Request evaluatedRequest, Room room) {
        if (room.isBooked())
            return false;
        else if (evaluatedRequest.getNumberOdSeatsNeeded() > room.getNumberSeats())
            return false;
        else
            return true;
    }
}
