package org.Marv1n.code;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Rafaël on 21/01/2015.
 */
public class RoomReserver implements Runnable {

    private static final Integer DEFAULT_TIMER = 180;

    private Integer timer;
    private Queue<Reservation> pendingReservation;
    private List<Room> rooms;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;

    public void Initialize() {
        this.timer = this.DEFAULT_TIMER;
        this.pendingReservation = new PriorityQueue<>();
        this.rooms = new ArrayList<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.startScheduler();
    }

    public Boolean haveRooms() {
        return !this.rooms.isEmpty();
    }

    public void AddRoom(Room room) {
        this.rooms.add(room);
    }

    public Boolean ReserveRoom(Reservation reservation) {
        if (!this.rooms.isEmpty()) {
            this.pendingReservation.add(reservation);
            return true;
        }
        return false;
    }

    public boolean HavePendingReservation() {
        return !this.pendingReservation.isEmpty();
    }

    public Integer GetReservationIntervalTimer() {
        return this.timer;
    }

    public void SetReservationIntervalTime(Integer timer) {
        this.timer = timer;
        this.restartScheduler();
    }

    @Override
    public void run() {
        for (Room room : this.rooms) {
            if (!room.IsReserved()) {
                room.Reserve(this.pendingReservation.remove());
            }
        }
    }

    private void restartScheduler() {
         this.future.cancel(true);
        this.startScheduler();
    }
    
    private void startScheduler() {
        this.future = this.scheduler.scheduleAtFixedRate(this, this.timer, this.timer, TimeUnit.SECONDS);
    }
}
