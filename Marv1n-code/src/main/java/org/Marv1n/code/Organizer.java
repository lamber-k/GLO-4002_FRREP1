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
    private Integer maximunPendingRequests;

    public void initialize(TaskScheduler scheduler, Integer maximunPendingRequests) {
        this.pendingRequest = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximunPendingRequests = maximunPendingRequests;
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
        if(this.pendingRequest.size() >= this.maximunPendingRequests) {
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

    public Integer getMaximunPendingRequests() {
        return this.maximunPendingRequests;
    }

    public void setMaximunPendingRequests(Integer maximunPendingRequests) {
        this.maximunPendingRequests = maximunPendingRequests;
    }
}

