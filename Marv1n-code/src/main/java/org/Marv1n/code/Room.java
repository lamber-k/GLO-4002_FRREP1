package org.Marv1n.code;


public class Room {

    Request request;
    private int numberSeats;

    public Room(int numberOfSeats) {
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

    public int getNumberSeats() {
        return numberSeats;
    }
}
