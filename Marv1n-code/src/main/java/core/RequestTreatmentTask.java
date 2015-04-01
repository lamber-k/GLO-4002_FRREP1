package core;

import core.request.Request;
import core.request.evaluation.EvaluationNoRoomFoundException;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.Room;
import core.room.RoomAlreadyReservedException;
import core.room.RoomRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestTreatmentTask extends Task {

    private static final Logger log = Logger.getLogger( RequestTreatmentTask.class.getName() );

    private EvaluationStrategy evaluationStrategy;
    private SortingRequestStrategy sortingRequestStrategy;
    private RoomRepository roomRepository;
    private List<Request> requestsToTreat;

    RequestTreatmentTask(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> requestsToTreat) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = strategyAssignation;
        this.sortingRequestStrategy = strategySortRequest;
        this.requestsToTreat = requestsToTreat;
    }

    @Override
    protected void runTask() {
        treatPendingRequest();
    }

    protected void treatPendingRequest() {
        List<Request> sortedRequests = sortingRequestStrategy.sortList(requestsToTreat);
        for (Request pendingRequest : sortedRequests) {
            try {
                Room roomFound = evaluationStrategy.evaluateOneRequest(roomRepository, pendingRequest);
                roomFound.reserve(pendingRequest);
            } catch (EvaluationNoRoomFoundException e) {
                log.log(Level.FINEST, "No Room Found Exception:", e);
            } catch (RoomAlreadyReservedException e) {
                log.log(Level.FINEST, "Already Reserved Exception:", e);
            }
        }
    }
}
