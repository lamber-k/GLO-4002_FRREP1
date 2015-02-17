package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public interface IReservable {

    public Boolean isBooked();

    public void book(Request request) throws ExceptionReservableAlreadyBooked, ExceptionReservableInsufficientCapacity;

    public Integer getNumberSeats();

<<<<<<< HEAD:Marv1n-code/src/main/java/org/Marv1n/code/Reservable/IReservable.java
    public boolean hasGreaterCapacityThan(IReservable IReservable);
=======
    public boolean hasGreaterCapacityThan(Reservable reservable);

    public Integer compareReservableCapacity(Reservable reservable);

    public boolean hasEnoughCapacity(Integer capacityNeeded);
>>>>>>> Reservation System. Accepted ?:Marv1n-code/src/main/java/org/Marv1n/code/Reservable/Reservable.java
}
