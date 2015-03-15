package org.Marv1n.code.Reservable;

public interface IReservable {

    public int getNumberOfSeats();

    public String getName();

    public boolean hasGreaterOrEqualCapacityThan(IReservable reservable);

    public int compareReservableCapacity(IReservable reservable);

    public boolean hasEnoughCapacity(int capacityNeeded);
}
