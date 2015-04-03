package ca.ulaval.glo4002.core.request.evaluation;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public Room evaluateOneRequest(RoomRepository reservables, Request evaluatedRequest) {
        Room betterRoom = null;
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                if (betterRoom == null && room.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded())) {
                    betterRoom = room;
                } else {
                    betterRoom = room.getBestFit(betterRoom, evaluatedRequest.getNumberOfSeatsNeeded());
                }
            }
        }
        if (betterRoom == null) {
            throw new EvaluationNoRoomFoundException();
        }
        return betterRoom;
    }
}
