package ca.ulaval.glo4002.core.room;


import ca.ulaval.glo4002.core.request.Request;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Room {

    @Id
    private final UUID roomID;
    private int numberOfSeats;
    private String name;
    @OneToOne
    private Request associatedRequest = null;

    public Room(int numberOfSeats, String name) {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
        this.name = name;
    }

    public Room() {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = 0;
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public boolean isReserved() {
        return associatedRequest != null;
    }

    public void book(Request request) throws RoomAlreadyReservedException {
        if (associatedRequest != null) {
            throw new RoomAlreadyReservedException();
        }
        associatedRequest = request;
    }

    public void unbook() {
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
