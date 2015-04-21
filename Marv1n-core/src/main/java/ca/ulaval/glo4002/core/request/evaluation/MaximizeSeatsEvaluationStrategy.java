package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public Room evaluateOneRequest(RoomRepository reservables, Request evaluatedRequest) {
        Room bestRoom = null;
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                bestRoom = room.getBestFit(bestRoom, evaluatedRequest.getNumberOfSeatsNeeded());
            }
        }
        if (bestRoom == null) {
            throw new EvaluationNoRoomFoundException();
        }
        return bestRoom;
    }
}
