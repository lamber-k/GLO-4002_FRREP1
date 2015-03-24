package org.Marv1n.core.Room;

public interface Room {

    int getNumberOfSeats();

    String getName();

    boolean hasGreaterOrEqualCapacityThan(Room room);

    int compareReservableCapacity(Room room);

    boolean hasEnoughCapacity(int capacityNeeded);
}
