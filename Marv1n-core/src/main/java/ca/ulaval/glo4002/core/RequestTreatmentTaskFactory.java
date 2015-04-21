package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.List;

public class RequestTreatmentTaskFactory implements TaskFactory {

    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private PendingRequests pendingRequests;
    private NotificationFactory notificationFactory;
    private RequestRepository requestRepository;

    public RequestTreatmentTaskFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, PendingRequests pendingRequest, NotificationFactory notificationFactory, RequestRepository requestRepository) {
        this.roomRepository = roomRepository;
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.pendingRequests = pendingRequest;
        this.notificationFactory = notificationFactory;
        this.requestRepository = requestRepository;
    }

    @Override
    public Task createTask() {
        //TODO ALL Test me properly
        List<Request> requestToTreat = pendingRequests.retrieveCurrentPendingRequest();
        return new RequestTreatmentTask(strategyAssignation, strategySortRequest, roomRepository, requestToTreat, notificationFactory, requestRepository);
    }
}
