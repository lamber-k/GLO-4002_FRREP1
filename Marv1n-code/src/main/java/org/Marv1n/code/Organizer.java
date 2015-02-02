package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Organizer implements Runnable {

    private Queue<Request> pendingRequest;
    private List<Room> rooms;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests) {
        this.pendingRequest = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
    }

    public Boolean hasRoom() {
        return !this.rooms.isEmpty();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addRequest(Request request) {
        if (this.rooms.isEmpty()) {
            throw new NoRoomAvailableException();
        }
        this.pendingRequest.add(request);
        if(this.pendingRequest.size() >= this.maximumPendingRequests) {
            this.treatPendingRequestsNow();
        }
    }

    public boolean hasPendingRequest() {
        return !this.pendingRequest.isEmpty();
    }

    public void treatPendingRequestsNow() {
        this.taskScheduler.runNow(this);
    }

    public void treatPendingRequest() {
        for (Room room : this.rooms) {
            if (!room.isBooked()) {
                room.book(this.pendingRequest.remove());
            }
        }
    }

    @Override
    public void run() {
        this.treatPendingRequest();
    }

    public Integer getMaximumPendingRequests() {
        return this.maximumPendingRequests;
    }

    public void setMaximumPendingRequests(Integer maximumPendingRequests) {
        this.maximumPendingRequests = maximumPendingRequests;
    }
}

