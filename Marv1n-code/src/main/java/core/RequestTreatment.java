package core;

import core.request.Request;
import core.request.RequestRepository;
import core.request.RequestStatus;
import core.request.evaluation.EvaluationNoRoomFoundException;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.Room;
import core.room.RoomAlreadyReservedException;
import core.room.RoomInsufficientSeatsException;
import core.room.RoomRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//TODO ALL feed back des erreurs
public class RequestTreatment extends RunnableRequestTreatment {

    private EvaluationStrategy assigner;
    private SortingRequestStrategy requestSorter;
    private RoomRepository reservables;
    private RequestRepository requests;

    RequestTreatment(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, RequestRepository requestRepository) {
        this.reservables = roomRepository;
        this.assigner = strategyAssignation;
        this.requestSorter = strategySortRequest;
        this.requests = requestRepository;
    }

    @Override
    protected void treatPendingRequest() {
        List<Request> pendingRequests = requests.findAllPendingRequest();
        List<Request> sortedRequests = requestSorter.sortList(pendingRequests);
        for (Request pendingRequest : sortedRequests) {
            try {
                Room roomFound = assigner.evaluateOneRequest(reservables, pendingRequest);
                roomFound.reserve(pendingRequest);
            } catch (EvaluationNoRoomFoundException e) {

            } catch (RoomAlreadyReservedException e) {
                e.printStackTrace();
            }
        }
    }
}
