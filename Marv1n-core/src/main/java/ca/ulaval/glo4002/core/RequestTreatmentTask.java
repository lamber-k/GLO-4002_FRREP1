package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationNoRoomFoundException;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.List;

public class RequestTreatmentTask implements Task {

    private EvaluationStrategy evaluationStrategy;
    private SortingRequestStrategy sortingRequestStrategy;
    private RoomRepository roomRepository;
    private List<Request> requestsToTreat;
    private NotificationFactory notificationFactory;
    private RequestRepository requestRepository;

    RequestTreatmentTask(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> requestsToTreat, NotificationFactory notificationFactory, RequestRepository requestRepository) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = strategyAssignation;
        this.sortingRequestStrategy = strategySortRequest;
        this.requestsToTreat = requestsToTreat;
        this.notificationFactory = notificationFactory;
        this.requestRepository = requestRepository;
    }

    @Override
    public void run() {
        treatPendingRequest();
    }

    protected void treatPendingRequest() {
        List<Request> sortedRequests = sortingRequestStrategy.sortList(requestsToTreat);
        for (Request pendingRequest : sortedRequests) {
            Room roomFound = null;
            try {
                roomFound = evaluationStrategy.evaluateOneRequest(roomRepository, pendingRequest);
                roomFound.book(pendingRequest);
                pendingRequest.reserve(roomFound);
            } catch (EvaluationNoRoomFoundException e) {
                pendingRequest.refuse(e.getMessage());
            } catch (RoomAlreadyReservedException e) {
                pendingRequest.refuse(e.getMessage());
            }
            roomRepository.persist(roomFound);
            requestRepository.persist(pendingRequest);
            Notification notification = notificationFactory.createNotification(pendingRequest);
            notification.announce();
        }
    }
}
