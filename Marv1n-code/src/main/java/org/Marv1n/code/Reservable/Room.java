package org.Marv1n.code.Reservable;

import org.Marv1n.code.Request;

public class Room implements Reservable {

    private Request request;
    private Integer numberSeats;

    public Room(Integer numberOfSeats) {
        this.numberSeats = numberOfSeats;
        this.request = null;
    }

    public Boolean isBooked() {
        return this.request != null;
    }

    public void book(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }

    public Integer getNumberSeats() {
        return this.numberSeats;
    }

    public boolean hasGreaterCapacityThan(Reservable reservable) {
        return this.getNumberSeats() > reservable.getNumberSeats();
    }
}
