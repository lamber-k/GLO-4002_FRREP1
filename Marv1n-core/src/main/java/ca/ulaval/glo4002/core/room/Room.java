package ca.ulaval.glo4002.core.room;


import ca.ulaval.glo4002.core.request.Request;

import java.util.UUID;

public class Room {

    private final UUID roomID;
    private int numberOfSeats;
    private String name;
    private Request associatedRequest = null;

    public Room(int numberOfSeats, String name) {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isReserved() {
        return associatedRequest != null;
    }

    public void reserve(Request request) {
        if (associatedRequest != null) {
            request.refuse();
            throw new RoomAlreadyReservedException();
        }
        request.accept();
        associatedRequest = request;
    }

    public void cancelReservation() {
        associatedRequest = null;
    }

    public Room getBestFit(Room room, int capacityNeeded) {
        if (!hasEnoughCapacity(capacityNeeded) && !room.hasEnoughCapacity(capacityNeeded)) {
            throw new RoomInsufficientSeatsException();
        } else if (!hasEnoughCapacity(capacityNeeded)) {
            return room;
        } else if (!room.hasEnoughCapacity(capacityNeeded)) {
            return this;
        } else if (numberOfSeats > room.numberOfSeats)
            return room;
        return this;
    }

    public boolean hasEnoughCapacity(int capacityNeeded) {
        return numberOfSeats >= capacityNeeded;
    }

    @Override
    public int hashCode() {
        return roomID.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs != null && rhs instanceof Room && hashCode() == rhs.hashCode();
    }

    public Request getRequest() {
        return associatedRequest;
    }
}
