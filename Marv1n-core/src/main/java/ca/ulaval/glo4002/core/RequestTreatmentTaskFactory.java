package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.mail.MailSender;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RequestTreatmentTaskFactory implements TaskFactory {

    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private List<Request> pendingRequest;
    private MailSender mailSender;
    private PersonRepository personRepository;

    public RequestTreatmentTaskFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, List<Request> pendingRequest, MailSender mailSender, PersonRepository personRepository) {
        this.roomRepository = roomRepository;
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.pendingRequest = pendingRequest;
        this.mailSender = mailSender;
        this.personRepository = personRepository;
    }

    @Override
    public Task createTask() {
        List<Request> requestToTreat = new ArrayList<>();
        requestToTreat.addAll(pendingRequest);
        pendingRequest.removeAll(requestToTreat);
        return new RequestTreatmentTask(strategyAssignation, strategySortRequest, roomRepository, requestToTreat, mailSender, personRepository);
    }
}
