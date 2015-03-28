package core.reservation;

import core.request.Request;
import core.room.Room;

public class Reservation {

    private Request request;
    private Room reserved;

    public Reservation(Request request, Room reserved) {
        this.request = request;
        this.reserved = reserved;
    }

    public Request getRequest() {
        return request;
    }

    public Room getReserved() {
        return reserved;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Reservation) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (request.hashCode() * 3) + (reserved.hashCode() * 5);
    }
}
