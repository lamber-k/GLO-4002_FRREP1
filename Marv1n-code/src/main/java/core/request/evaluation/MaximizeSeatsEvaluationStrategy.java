package core.request.evaluation;

import core.request.Request;
import core.room.Room;
import core.room.RoomRepository;
import core.room.RoomInsufficientSeatsException;

public class MaximizeSeatsEvaluationStrategy implements EvaluationStrategy {

    @Override
    public Room evaluateOneRequest(RoomRepository reservables, Request evaluatedRequest) throws EvaluationNoRoomFoundException {
        Room betterRoom = null;
        for (Room room : reservables.findAll()) {
            if (!room.isReserved()) {
                if (betterRoom == null && room.hasEnoughCapacity(evaluatedRequest.getNumberOfSeatsNeeded())) {
                    betterRoom = room;
                } else {
                    try {
                        betterRoom = room.getBestFit(betterRoom, evaluatedRequest.getNumberOfSeatsNeeded());
                    } catch (RoomInsufficientSeatsException roomInsufficientSeats) {
                    }
                }
            }
        }
        if (betterRoom == null) {
            throw new EvaluationNoRoomFoundException();
        }
        return betterRoom;
    }
}
