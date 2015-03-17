package org.Marv1n.code.Reservable;

public interface Reservable {

    public int getNumberOfSeats();

    public String getName();

    public boolean hasGreaterOrEqualCapacityThan(Reservable reservable);

    public int compareReservableCapacity(Reservable reservable);

    public boolean hasEnoughCapacity(int capacityNeeded);
}
