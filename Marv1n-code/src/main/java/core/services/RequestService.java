package core.services;

import core.ObjectNotFoundException;
import core.request.Request;
import core.request.RequestRepository;
import infrastructure.locator.LocatorService;

import java.util.Optional;
import java.util.UUID;

public class RequestService {
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestService() {
        this.requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
    }

    public void addRequest(Request request) {
        requestRepository.persist(request);
    }

    public Request getRequestByEmailAndId(String email, UUID id) {
        Optional<Request> optionalRequest = requestRepository.findByUUID(id);
        if(optionalRequest.isPresent()){
            return optionalRequest.get();
        }
        throw new ObjectNotFoundException();
    }
}
