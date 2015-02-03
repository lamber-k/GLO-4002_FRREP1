package org.Marv1n.code;

import java.util.ArrayList;
import java.util.List;

public class StrategyAssignationMaximiseSeats implements StrategyAssignation {

    @Override
    public void assingRooms(List<Request> requests, List<Room> rooms) {
        List<Request> unhandledRequests = new ArrayList<>();
        Room bestRoom;
        while (!requests.isEmpty()) {
            bestRoom = null;
            for (Room room : rooms) {
                if (!room.isBooked()) {
                    if (requests.get(0).getSeatsNeeded() <= room.getNumberSeats()) {
                        if (bestRoom == null)
                            bestRoom = room;
                        else if (bestRoom.getNumberSeats() > room.getNumberSeats())
                            bestRoom = room;
                    }
                }
            }
            if (bestRoom != null)
                bestRoom.book(requests.get(0));
            else
                unhandledRequests.add(requests.get(0));
            requests.remove(0);
        }
        for(Request request : unhandledRequests)
            requests.add(request);
    }
}
