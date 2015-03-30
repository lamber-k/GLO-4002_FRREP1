package org.Marv1n.core.room;

import org.Marv1n.core.request.Request;

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

    public void reserve(Request request) throws RoomIsAlreadyReserved, RoomInsufficientSeats {
        if (associatedRequest != null) {
            throw new RoomIsAlreadyReserved();
        }
        if (request.getNumberOfSeatsNeeded() > numberOfSeats) {
            throw new RoomInsufficientSeats();
        }
        associatedRequest = request;
    }

    public void cancelReservation() {
        associatedRequest = null;
    }

    public Room getBestFit(Room room, int capacityNeeded) throws RoomInsufficientSeats {
        if (!hasEnoughCapacity(capacityNeeded) && !room.hasEnoughCapacity(capacityNeeded)) {
            throw new RoomInsufficientSeats();
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

    public int hashCode() {
        return roomID.hashCode();
    }

    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Room) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }
}
