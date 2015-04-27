package ca.ulaval.glo4002.applicationServices.services;

import ca.ulaval.glo4002.applicationServices.locator.LocatorService;
import ca.ulaval.glo4002.applicationServices.models.RequestInformationModel;
import ca.ulaval.glo4002.applicationServices.models.RequestModel;
import ca.ulaval.glo4002.applicationServices.models.RequestNotAcceptedInformationModel;
import ca.ulaval.glo4002.applicationServices.models.RequestsInformationModel;
import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestService {

    private static final String ERROR_REQUEST_BY_EMAIL_AND_ID = "Il n'existe pas de demande \"%s\" pour l'organisateur \"%s\"";
    private static final String ERROR_REQUEST_EMAIL_BAD_FORMAT = "Le courriel \"%s\" n'est pas valide";
    private RequestRepository requestRepository;
    private RoomRepository roomRepository;
    private PendingRequests pendingRequests;
    private EmailValidator emailValidator;

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        this.roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
        this.pendingRequests = LocatorService.getInstance().resolve(PendingRequests.class);
        this.emailValidator = LocatorService.getInstance().resolve(EmailValidator.class);
    }

    public Request addRequest(RequestModel model) throws InvalidFormatException {
        Person responsible = new Person(model.getCourrielOrganisateur());
        if (!responsible.isValid(this.emailValidator)) {
            throw new InvalidFormatException(String.format(ERROR_REQUEST_EMAIL_BAD_FORMAT, responsible.getMailAddress()));
        }
        List<Person> participant = model.getParticipantsCourriels().stream().map(Person::new).collect(Collectors.toList());
        for (Person person : participant) {
            if (!person.isValid(this.emailValidator)) {
                throw new InvalidFormatException(String.format(ERROR_REQUEST_EMAIL_BAD_FORMAT, person.getMailAddress()));
            }
        }
        Request request = new Request(model.getNombrePersonne(), model.getPriorite(), responsible, participant);
        pendingRequests.addRequest(request);
        return request;
    }

    public RequestInformationModel getRequestByEmailAndId(String email, UUID id) throws ObjectNotFoundException {
        Request currentRequest;
        try {
            currentRequest = requestRepository.findByUUID(id);
        } catch (RequestNotFoundException exception) {
            Optional<Request> foundRequest = getPendingRequestByID(id);
            if (foundRequest.isPresent()) {
                currentRequest = foundRequest.get();
                Person responsible = currentRequest.getResponsible();
                if (email.equals(responsible.getMailAddress())) {
                    return new RequestInformationModel(currentRequest);
                }
            }
            throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email), exception);
        }
        try {
            Person responsible = currentRequest.getResponsible();
            Room currentRoom = roomRepository.findRoomByAssociatedRequest(currentRequest);
            if (email.equals(responsible.getMailAddress())) {
                return new RequestInformationModel(currentRequest.getNumberOfSeatsNeeded(), responsible.getMailAddress(), currentRequest.getRequestStatus(), currentRoom.getName());
            }
        } catch (RoomNotFoundException exception) {
            throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email), exception);
        }
        throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email));
    }

    public RequestsInformationModel getRequestByEmail(String email) throws ObjectNotFoundException {
        List<Request> requests = new ArrayList<>();
        List<RequestInformationModel> accepted = new ArrayList<>();
        List<RequestNotAcceptedInformationModel> others = new ArrayList<>();

        try {
            requests.addAll(requestRepository.findByResponsibleMail(email));
        } catch (RequestNotFoundException exception) {
            //TODO exception ?
        }
        requests.addAll(getPendingRequestByResponsibleMail(email));
        if (requests.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        requests.stream()
                .sorted((x, y) -> Long.compare(x.getCreationDate(), y.getCreationDate()))
                .forEach(r -> {
                    if (r.getRequestStatus().equals(RequestStatus.ACCEPTED)) {
                        accepted.add(new RequestInformationModel(r));
                    } else {
                        others.add(new RequestNotAcceptedInformationModel(r));
                    }
                });
        return new RequestsInformationModel(accepted, others);
    }

    private List<Request> getPendingRequestByResponsibleMail(String mail) {
        return pendingRequests.getCurrentPendingRequest().stream().filter(r -> r.getResponsible().getMailAddress().equals(mail)).collect(Collectors.toList());
    }

    private Optional<Request> getPendingRequestByID(UUID id) {
        return pendingRequests.getCurrentPendingRequest().stream().filter(r -> r.getRequestID().equals(id)).findAny();
    }
}
