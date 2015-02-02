package org.Marv1n.code;

import java.util.List;
import java.util.Queue;

/**
 * Created by Kevin on 02/02/2015.
 */
public interface Strategy {
    public Queue<Request>   sortRequests(Queue<Request> requests, List<Room> rooms);
}
