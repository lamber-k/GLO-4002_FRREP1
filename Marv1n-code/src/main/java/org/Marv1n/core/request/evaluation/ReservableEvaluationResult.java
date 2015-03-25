package org.Marv1n.core.request.evaluation;

import org.Marv1n.core.room.Room;

public class ReservableEvaluationResult {

    private Room matchingRoom = null;

    public ReservableEvaluationResult(Room Room) {
        this.matchingRoom = Room;
    }

    public ReservableEvaluationResult() {
    }

    public boolean matchFound() {
        return matchingRoom != null;
    }

    public Room getBestReservableMatch() {
        return matchingRoom;
    }
}
