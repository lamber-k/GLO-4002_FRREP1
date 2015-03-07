package org.Marv1n.code.Reservable;

public interface IReservable {

    public int getNumberSeats();

    public boolean hasGreaterCapacityThan(IReservable reservable);

    public int compareReservableCapacity(IReservable reservable);

    public boolean hasEnoughCapacity(Integer capacityNeeded);
}
