package core.services;

import core.request.Request;
import core.request.RequestRepository;

import java.util.UUID;

public class RequestService {
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void AddRequest() {

    }

    public Request GetRequestByEmailAndId(String email, UUID id) {
        return null;
    }
}
