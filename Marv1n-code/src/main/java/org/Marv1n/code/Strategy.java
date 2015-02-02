package org.Marv1n.code;

import java.util.List;
import java.util.Queue;

public interface Strategy {
    public Queue<Request>   sortRequests(Queue<Request> requests, List<Room> rooms);
}
