package org.Marv1n.core;

import org.Marv1n.core.request.evaluation.EvaluationNoRoomFoundException;
import org.Marv1n.core.request.evaluation.EvaluationStrategy;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestRepository;
import org.Marv1n.core.room.RoomRepository;
import org.Marv1n.core.request.sorting.SortingRequestStrategy;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomInsufficientSeatsException;
import org.Marv1n.core.room.RoomIsAlreadyReservedException;

import java.util.List;

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
                Room chosenRoom = assigner.evaluateOneRequest(reservables, pendingRequest);
                chosenRoom.reserve(pendingRequest);
            } catch (EvaluationNoRoomFoundException e) {
                //pendingRequest.refused();
            } catch (RoomInsufficientSeatsException roomInsufficientSeats) {
                //pendingRequest.refused();
            } catch (RoomIsAlreadyReservedException roomIsAlreadyReserved) {
                //pendingRequest.refused();
            }
            //pendingRequest.accepted();
        }
    }
}
