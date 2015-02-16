package org.Marv1n.code;

public interface Reservable {

    public Boolean isBooked();

    public void book(Request request);

    public Request getRequest();

    public Integer getNumberSeats();

    public boolean hasGreaterCapacityThan(Reservable reservable);
}
