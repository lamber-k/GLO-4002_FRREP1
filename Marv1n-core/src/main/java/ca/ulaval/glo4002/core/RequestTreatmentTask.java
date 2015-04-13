package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.notification.mail.MailNotificationFactory;
import ca.ulaval.glo4002.core.notification.mail.MailSender;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.Request;
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
    private MailSender mailSender;
    private PersonRepository personRepository;

    RequestTreatmentTask(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> requestsToTreat, MailSender mailSender, PersonRepository personRepository) {
        this.roomRepository = roomRepository;
        this.evaluationStrategy = strategyAssignation;
        this.sortingRequestStrategy = strategySortRequest;
        this.requestsToTreat = requestsToTreat;
        this.mailSender = mailSender;
        this.personRepository = personRepository;
    }

    @Override
    public void run() {
        treatPendingRequest();
    }

    protected void treatPendingRequest() {
        MailNotificationFactory mailNotificationFactory = new MailNotificationFactory(mailSender, personRepository);
        List<Request> sortedRequests = sortingRequestStrategy.sortList(requestsToTreat);
        for (Request pendingRequest : sortedRequests) {
            Room roomFound = null;
            try {
                roomFound = evaluationStrategy.evaluateOneRequest(roomRepository, pendingRequest);
                pendingRequest.reserve(roomFound);
            } catch (EvaluationNoRoomFoundException e) {
                pendingRequest.refuse(e.getMessage());
            } catch (RoomAlreadyReservedException e) {
                pendingRequest.refuse(e.getMessage());
            }
            try {
                roomRepository.persist(roomFound);
            } catch (InvalidFormatException e) {

            }
            mailNotificationFactory.createNotification(pendingRequest).announce();
        }
    }
}
