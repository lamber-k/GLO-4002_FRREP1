package org.Marv1n.code;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Rafaël on 21/01/2015.
 */
public class Organizer implements Runnable {

    private static final Integer DEFAULT_TIMER = 180;

    private Integer timer;
    private Queue<Request> pendingRequest;
    private List<Room> rooms;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private boolean isSchedulerRunning;

    public void Initialize() {
        this.timer = DEFAULT_TIMER;
        this.isSchedulerRunning = false;
        this.pendingRequest = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public Boolean HasRoom() {
        return !this.rooms.isEmpty();
    }

    public void AddRoom(Room room) {
        this.rooms.add(room);
    }

    public void AddRequest(Request request) {
        if (this.rooms.isEmpty()) {
            throw new NoRoomAvailableException();
        }
        this.pendingRequest.add(request);
    }

    public boolean HasPendingRequest() {
        return !this.pendingRequest.isEmpty();
    }

    public Integer GetReservationIntervalTimer() {
        return this.timer;
    }

    public void SetReservationInterval(Integer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        for (Room room : this.rooms) {
            if (!room.IsBooked()) {
                room.Book(this.pendingRequest.remove());
            }
        }
    }

    public void RestartScheduler() {
        this.future.cancel(true);
        this.StartScheduler();
    }

    public void StartScheduler() {
        this.future = this.scheduler.scheduleAtFixedRate(this, this.timer, this.timer, TimeUnit.SECONDS);
        this.isSchedulerRunning = true;
    }

    public void CancelScheduler() {
        if (this.isSchedulerRunning) {
            this.future.cancel(true);
            this.isSchedulerRunning = false;
        }
    }

    public boolean IsSchedulerRunning() {
        return this.isSchedulerRunning;
    }

    public void TreatPendingRequestsNow() {
        ScheduledFuture<?> newTask = this.scheduler.schedule(this, 0, TimeUnit.SECONDS);
        try {
            newTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

