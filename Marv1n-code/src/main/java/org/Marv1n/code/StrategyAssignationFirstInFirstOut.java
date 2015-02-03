package org.Marv1n.code;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Queue;

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
