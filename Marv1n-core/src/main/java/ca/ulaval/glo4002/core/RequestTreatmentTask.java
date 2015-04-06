package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationNoRoomFoundException;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.List;

public class RequestTreatmentTask extends Task {

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
                //TODO updating room in repository
                //TODO adding request to repository
            } catch (EvaluationNoRoomFoundException e) {
                //TODO handle this, setting request statut to refused and adding it to the repository
            } catch (RoomAlreadyReservedException e) {
                //TODO handle this.
            }
        }
    }
}
