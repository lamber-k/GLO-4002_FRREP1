package org.Marv1n.core.Room;

import java.util.UUID;

public class MeetingRoom implements Room {

    private final UUID roomID;
    private int numberOfSeats;
    private String name;

    public MeetingRoom(int numberOfSeats, String name) {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
        this.name = name;
    }

    @Override
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasGreaterOrEqualCapacityThan(Room room) {
        return numberOfSeats >= room.getNumberOfSeats();
    }

    @Override
    public int compareReservableCapacity(Room room) {
        return numberOfSeats - room.getNumberOfSeats();
    }

    @Override
    public boolean hasEnoughCapacity(int capacityNeeded) {
        return numberOfSeats >= capacityNeeded;
    }

    @Override
    public int hashCode() {
        return roomID.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof MeetingRoom) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }
}
