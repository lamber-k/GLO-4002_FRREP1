package org.Marv1n.code;

public class Room {

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

    public boolean hasGreaterCapacityThan(Room room) {
        return this.getNumberSeats() > room.getNumberSeats();
    }
}
