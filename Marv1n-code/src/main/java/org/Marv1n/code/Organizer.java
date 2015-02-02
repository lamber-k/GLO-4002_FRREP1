package org.Marv1n.code;

import org.Marv1n.code.exception.NoRoomAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;


public class Organizer implements Runnable {

    private static final Integer DEFAULT_TIMER = 120;
    private static final Integer NUMBER_OF_THREAD = 1;

    private Integer timer;
    private Queue<Request> pendingRequest;
    private List<Room> rooms;
    private TaskScheduler taskScheduler;

    public void initialize() {
        this.timer = DEFAULT_TIMER;
        this.pendingRequest = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        taskScheduler = new TaskScheduler(NUMBER_OF_THREAD, TimeUnit.MINUTES);
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
    }

    public boolean hasPendingRequest() {
        return !this.pendingRequest.isEmpty();
    }

    public Integer getReservationIntervalTimer() {
        return this.timer;
    }

    public void setOrganizerRunInterval(Integer timer) {
        this.timer = timer;
    }

    public void startScheduler() {
        this.taskScheduler.startScheduler(this.timer, this);
    }

    public void cancelScheduler() {
        this.taskScheduler.cancelScheduler();
    }

    public boolean isSchedulerRunning() {
        return this.taskScheduler.IsSchedulerRunning();
    }

    public void treatPendingRequestsNow() throws InterruptedException, ExecutionException {
       this.taskScheduler.runNow(this.timer, this);
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
}

