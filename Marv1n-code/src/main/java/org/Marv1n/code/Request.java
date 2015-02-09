package org.Marv1n.code;

public class Request {
    private int numberOfSeatsNeeded = 0;

    public Request(int numberOfSeatsNeeded) {
        this.numberOfSeatsNeeded = numberOfSeatsNeeded;
    }

    public int getNumberOdSeatsNeeded() {
        return this.numberOfSeatsNeeded;
    }
}
