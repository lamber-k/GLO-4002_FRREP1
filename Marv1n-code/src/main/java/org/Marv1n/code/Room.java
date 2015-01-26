package org.Marv1n.code;

/**
 * Created by maxime on 14/01/2015.
 */
public class Room {

    Request request;

    public Room() {
        this.request = null;
    }

    public Boolean IsBooked() {
        return this.request != null;
    }

    public void Book(Request request) {
        this.request = request;
    }


    public Request GetRequest() {
        return this.request;
    }
}
