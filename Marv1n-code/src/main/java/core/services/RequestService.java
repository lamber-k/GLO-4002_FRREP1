package core.services;

import core.request.Request;
import core.request.RequestRepository;
import infrastructure.locator.LocatorService;

import java.util.UUID;

public class RequestService {
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
    }

    public void AddRequest() {

    }

    public Request GetRequestByEmailAndId(String email, UUID id) {
        return null;
    }
}
