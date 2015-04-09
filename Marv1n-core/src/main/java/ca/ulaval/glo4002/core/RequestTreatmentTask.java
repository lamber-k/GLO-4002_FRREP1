package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
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
    private Thread previousTask;

    RequestTreatmentTask(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> requestsToTreat, Task previousTask) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = strategyAssignation;
        this.sortingRequestStrategy = strategySortRequest;
        this.requestsToTreat = requestsToTreat;
        this.previousTask = previousTask;
    }

    @Override
    protected void runTask() throws InterruptedException {
        if(previousTask != null) {
            previousTask.join();
        }
        treatPendingRequest();
    }

    protected void treatPendingRequest() {
        List<Request> sortedRequests = sortingRequestStrategy.sortList(requestsToTreat);
        for (Request pendingRequest : sortedRequests) {
            try {
                Room roomFound = evaluationStrategy.evaluateOneRequest(roomRepository, pendingRequest);
                pendingRequest.reserve(roomFound);
                roomRepository.persist(roomFound);
                //TODO adding request to repository
            } catch (EvaluationNoRoomFoundException e) {
                //TODO handle this, setting request status to refused and adding it to the repository
            } catch (RoomAlreadyReservedException e) {
                //TODO handle this.
            } catch (InvalidFormatException e) {
                //TODO handle this.
            }
        }
    }
}
