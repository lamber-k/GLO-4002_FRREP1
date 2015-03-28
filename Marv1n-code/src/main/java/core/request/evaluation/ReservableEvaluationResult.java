package core.request.evaluation;

import core.room.Room;

public class ReservableEvaluationResult {

    private Room matchingRoom = null;

    public ReservableEvaluationResult(Room room) {
        this.matchingRoom = room;
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
