package org.Marv1n.code;

/**
 * Created by maxime on 14/01/2015.
 */
public class Room {

    Reservation reservation;

    public Room() {
        this.reservation = null;
    }

    public Boolean IsReserved(){
        return this.reservation != null;
    }

    public void Reserve(Reservation reservation) {
        this.reservation = reservation;
    }


    public Reservation GetReservation() {
        return this.reservation;
    }
}
