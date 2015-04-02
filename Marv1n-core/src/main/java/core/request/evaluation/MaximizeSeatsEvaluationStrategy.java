package core.request.evaluation;

import core.request.Request;
import core.room.Room;
import core.room.RoomRepository;

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
