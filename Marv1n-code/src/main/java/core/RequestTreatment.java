package core;

import core.request.Request;
import core.request.evaluation.EvaluationNoRoomFoundException;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.Room;
import core.room.RoomAlreadyReservedException;
import core.room.RoomInsufficientSeatsException;
import core.room.RoomRepository;

import java.util.List;

public class RequestTreatment extends RunnableRequestTreatment {

    private EvaluationStrategy evaluationStrategy;
    private SortingRequestStrategy sortingRequestStrategy;
    private RoomRepository roomRepository;
    private List<Request> requestsToTreat;

    RequestTreatment(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> requestsToTreat) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = strategyAssignation;
        this.sortingRequestStrategy = strategySortRequest;
        this.requestsToTreat = requestsToTreat;
    }

    @Override
    protected void treatPendingRequest() {
        List<Request> sortedRequests = sortingRequestStrategy.sortList(requestsToTreat);
        for (Request pendingRequest : sortedRequests) {
            try {
                Room roomFound = evaluationStrategy.evaluateOneRequest(roomRepository, pendingRequest);
                roomFound.reserve(pendingRequest);
            } catch (EvaluationNoRoomFoundException e) {

            } catch (RoomInsufficientSeatsException e) {
                e.printStackTrace();
            } catch (RoomAlreadyReservedException e) {
                e.printStackTrace();
            }
        }
    }
}
