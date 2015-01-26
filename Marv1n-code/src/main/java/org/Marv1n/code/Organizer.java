package org.Marv1n.code;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;


public class Organizer implements Runnable {

    private static final Integer DEFAULT_TIMER = 180;

    private Integer timer;
    private Queue<Request> pendingRequest;
    private List<Room> rooms;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private boolean isSchedulerRunning;

    public void initialize() {
        this.timer = DEFAULT_TIMER;
        this.isSchedulerRunning = false;
        this.pendingRequest = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
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

    public void setReservationInterval(Integer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        for (Room room : this.rooms) {
            if (!room.isBooked()) {
                room.book(this.pendingRequest.remove());
            }
        }
    }

    public void startScheduler() {
        this.future = this.scheduler.scheduleAtFixedRate(this, this.timer, this.timer, TimeUnit.SECONDS);
        this.isSchedulerRunning = true;
    }

    public void cancelScheduler() {
        if (this.isSchedulerRunning) {
            this.future.cancel(true);
            this.isSchedulerRunning = false;
        }
    }

    public boolean isSchedulerRunning() {
        return this.isSchedulerRunning;
    }

    public void treatPendingRequestsNow() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> newTask = this.scheduler.schedule(this, 0, TimeUnit.SECONDS);
        newTask.get();
    }
}

