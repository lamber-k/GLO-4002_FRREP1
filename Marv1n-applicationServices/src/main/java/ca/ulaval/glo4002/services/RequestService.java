package ca.ulaval.glo4002.services;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.locator.LocatorService;
import ca.ulaval.glo4002.models.RequestInformationModel;
import ca.ulaval.glo4002.models.RequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestService {

    public static final String ERROR_REQUEST_BY_EMAIL_AND_ID = "Il n'existe pas de demande \"%s\" pour l'organisateur \"%s\"";
    private RequestRepository requestRepository;
    private RoomRepository roomRepository;
    private PendingRequests pendingRequests;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        this.roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
        this.pendingRequests = LocatorService.getInstance().resolve(PendingRequests.class);
    }

    public Request addRequest(RequestModel model) {
        Person responsible = new Person(model.getCourrielOrganisateur());
        List<Person> participant = model.getParticipantsCourriels().stream().map(Person::new).collect(Collectors.toList());
        Request request = new Request(model.getNombrePersonne(), model.getPriorite(), responsible, participant);
        pendingRequests.addRequest(request);
        return request;
    }

    public RequestInformationModel getRequestByEmailAndId(String email, UUID id) throws ObjectNotFoundException {
        Request currentRequest;
        try {
            currentRequest = requestRepository.findByUUID(id);
            Person responsible = currentRequest.getResponsible();
            Room currentRoom = roomRepository.findRoomByAssociatedRequest(currentRequest);
            if (responsible.getMailAddress().equals(email)) {
                return new RequestInformationModel(currentRequest.getNumberOfSeatsNeeded(), responsible.getMailAddress(), currentRequest.getRequestStatus(), currentRoom.getName());
            }
        } catch (RequestNotFoundException exception) {
            throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email), exception);
        } catch (RoomNotFoundException exception) {
            throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email), exception);
        }
        throw new ObjectNotFoundException(String.format(ERROR_REQUEST_BY_EMAIL_AND_ID, id.toString(), email));
    }
}
