package org.Marv1n.code.Reservable;

public interface IReservable {

    public int getNumberOfSeats();

    public String getName();

    public boolean hasGreaterCapacityThan(IReservable reservable);

    public int compareReservableCapacity(IReservable reservable);

    public boolean hasEnoughCapacity(int capacityNeeded);
}
