package ca.ulaval.glo4002.services;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.InvalidRequestFormatException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.locator.LocatorService;

import java.util.UUID;

public class RequestService {
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
    }

    public void addRequest(Request request) throws InvalidRequestFormatException {
        try {
            requestRepository.persist(request);
        } catch (InvalidFormatException e) {
            throw new InvalidRequestFormatException(e.getMessage());
        }
    }

    public Request getRequestByEmailAndId(String email, UUID id) {
        try {
            return requestRepository.findByUUID(id);
        } catch (RequestNotFoundException e) {
            throw new ObjectNotFoundException();
        }
    }
}
