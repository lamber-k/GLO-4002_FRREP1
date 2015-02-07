package org.Marv1n.code;

import java.util.ArrayList;
import java.util.List;

public class StrategyAssignationMaximiseSeats implements StrategyAssignation {

    private int firstElement = 0;

    @Override
    public void assingRooms(List<Request> requests, List<Room> rooms) {
        List<Request> unhandledRequests = new ArrayList<>();
        Room bestRoom;
        while (!requests.isEmpty()) {
            bestRoom = null;
            for (Room room : rooms) {
                if (!room.isBooked() && requests.get(this.firstElement).getSeatsNeeded() <= room.getNumberSeats()) {
                    if (bestRoom == null)
                        bestRoom = room;
                    else if (bestRoom.getNumberSeats() > room.getNumberSeats())
                        bestRoom = room;
                }
            }
            if (bestRoom != null)
                bestRoom.book(requests.get(this.firstElement));
            else
                unhandledRequests.add(requests.get(this.firstElement));
            requests.remove(this.firstElement);
        }
        requests.addAll(unhandledRequests);
    }
}
