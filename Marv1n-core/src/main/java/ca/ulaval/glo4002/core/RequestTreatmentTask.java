package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationNoRoomFoundException;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestTreatmentTask extends Task {

    private static final Logger LOGGER = Logger.getLogger(RequestTreatmentTask.class.getName());

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
                LOGGER.log(Level.FINEST, "No Room Found Exception:", e);
            } catch (RoomAlreadyReservedException e) {
                LOGGER.log(Level.FINEST, "Already Reserved Exception:", e);
            }
        }
    }
}
