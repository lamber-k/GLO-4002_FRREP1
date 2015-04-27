package ca.ulaval.glo4002.core.room;


import ca.ulaval.glo4002.core.request.Request;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    protected Room() {
        this.roomID = UUID.randomUUID();
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
        if (room == null || !room.hasEnoughCapacity(capacityNeeded)) {
            if (!hasEnoughCapacity(capacityNeeded)) {
                return null;
            }
            return this;
        } else {
            if (numberOfSeats > room.numberOfSeats || !hasEnoughCapacity(capacityNeeded)) {
                return room;
            }
            return this;
        }
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
