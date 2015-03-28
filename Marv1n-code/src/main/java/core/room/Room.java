package core.room;

import java.util.UUID;

public class Room {

    private final UUID roomID;
    private int numberOfSeats;
    private String name;
    private boolean isReserved;

    public Room(int numberOfSeats, String name, boolean isReserved) {
        this.roomID = UUID.randomUUID();
        this.numberOfSeats = numberOfSeats;
        this.name = name;
        this.isReserved = isReserved;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getName() {
        return name;
    }

    public void reserve() throws RoomAlreadyReservedException {
        if (isReserved) {
            throw new RoomAlreadyReservedException();
        }
        isReserved = true;
    }

    public void cancelReservation() {
        isReserved = false;
    }

    public boolean hasGreaterOrEqualCapacityThan(Room room) {
        return numberOfSeats >= room.getNumberOfSeats();
    }

    public int compareReservableCapacity(Room room) {
        return numberOfSeats - room.getNumberOfSeats();
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
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Room) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }
}
