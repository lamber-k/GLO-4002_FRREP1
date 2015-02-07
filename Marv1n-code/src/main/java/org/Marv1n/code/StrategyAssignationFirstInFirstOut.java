package org.Marv1n.code;

import java.util.List;

public class StrategyAssignationFirstInFirstOut implements StrategyAssignation {

    private int firstElement = 0;

    @Override
    public void assingRooms(List<Request> requests, List<Room> rooms) {
        boolean requestIsAssigned = true;
        while (!requests.isEmpty() && requestIsAssigned) {
            requestIsAssigned = false;
            for (Room room : rooms)
                if (!room.isBooked() && !requestIsAssigned) {
                    room.book(requests.get(this.firstElement));
                    requests.remove(this.firstElement);
                    requestIsAssigned = true;
                }
        }
    }
}
