package ca.ulaval.glo4002.services;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.InvalidRequestFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomNotFoundException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.locator.LocatorService;
import ca.ulaval.glo4002.models.RequestInformationModel;

import java.util.UUID;

public class RequestService {

    public static final String ErrorRequestByEmailAndId = "Il n'existe pas de demande \"%s\" pour l'organisateur \"%s\"";
    private RequestRepository requestRepository;
    private RoomRepository roomRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        this.roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
    }

    public void addRequest(Request request) throws InvalidRequestFormatException {
        try {
            requestRepository.persist(request);
        } catch (InvalidFormatException exception) {
            throw new InvalidRequestFormatException(exception);
        }
    }

    public RequestInformationModel getRequestByEmailAndId(String email, UUID id) {
        Request currentRequest;
        try {
            currentRequest = requestRepository.findByUUID(id);
            Person responsible = currentRequest.getResponsible();
            Room currentRoom = roomRepository.findRoomByAssociatedRequest(currentRequest);
            if (responsible.getMailAddress().equals(email)) {
                return new RequestInformationModel(currentRequest.getNumberOfSeatsNeeded(), responsible.getMailAddress(), currentRequest.getRequestStatus(), currentRoom.getName());
            }
        } catch (RequestNotFoundException exception) {
            throw new ObjectNotFoundException(String.format(ErrorRequestByEmailAndId, id.toString(), email), exception);
        } catch (RoomNotFoundException exception) {
            throw new ObjectNotFoundException(String.format(ErrorRequestByEmailAndId, id.toString(), email), exception);
        }
        throw new ObjectNotFoundException(String.format(ErrorRequestByEmailAndId, id.toString(), email));
    }
}
