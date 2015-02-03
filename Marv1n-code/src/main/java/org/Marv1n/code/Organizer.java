package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;

import java.util.ArrayList;
import java.util.List;

public class Organizer implements Runnable {

    private List<Request> pendingRequest;
    private List<Room> rooms;
    private TaskScheduler taskScheduler;
    private Integer maximumPendingRequests;
    private StrategyAssignation assignator;

    public void initialize(TaskScheduler scheduler, Integer maximumPendingRequests, StrategyAssignation strategyAssignation) {
        this.pendingRequest = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.taskScheduler = scheduler;
        this.maximumPendingRequests = maximumPendingRequests;
        this.assignator = strategyAssignation;
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
        if (this.pendingRequest.size() >= this.maximumPendingRequests) {
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
        assignator.assingRooms(pendingRequest, rooms);
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

