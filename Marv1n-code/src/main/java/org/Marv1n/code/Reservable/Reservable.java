package org.Marv1n.code.Reservable;

public interface Reservable {

    int getNumberOfSeats();

    String getName();

    boolean hasGreaterOrEqualCapacityThan(Reservable reservable);

    int compareReservableCapacity(Reservable reservable);

    boolean hasEnoughCapacity(int capacityNeeded);
}
