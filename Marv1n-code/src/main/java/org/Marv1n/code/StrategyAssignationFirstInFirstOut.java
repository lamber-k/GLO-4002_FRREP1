package org.Marv1n.code;

import java.util.List;

public class StrategyAssignationFirstInFirstOut implements StrategyAssignation {
    @Override
    public void assingRooms(List<Request> requests, List<Room> rooms) {
        boolean requestIsAssigned = true;
        while (!requests.isEmpty() && requestIsAssigned) {
            requestIsAssigned = false;
            for (Room room : rooms)
                if (!room.isBooked() && !requestIsAssigned) {
                    room.book(requests.get(0));
                    requests.remove(0);
                    requestIsAssigned = true;
                }
        }
    }
}
