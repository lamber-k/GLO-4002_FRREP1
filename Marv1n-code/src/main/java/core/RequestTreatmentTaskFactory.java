package core;

import core.request.Request;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RequestTreatmentTaskFactory implements TaskFactory {

    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private List<Request> pendingRequest;

    public RequestTreatmentTaskFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> pendingRequest) {
        this.roomRepository = roomRepository;
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.pendingRequest = pendingRequest;
    }

    @Override
    public Thread createTask() {
        List<Request> requestToTreat = new ArrayList<>();
        requestToTreat.addAll(pendingRequest);
        pendingRequest.removeAll(requestToTreat);
        Runnable requestTreatmentTask = new RequestTreatmentTask(strategyAssignation, strategySortRequest, roomRepository, requestToTreat);
        return new Thread(requestTreatmentTask);
    }
}
