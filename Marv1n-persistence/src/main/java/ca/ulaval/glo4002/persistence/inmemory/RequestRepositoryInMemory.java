package ca.ulaval.glo4002.persistence.inmemory;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestRepositoryInMemory extends RepositoryInMemory<Request> implements RequestRepository {

    @Override
    public Request findByUUID(UUID id) throws RequestNotFoundException {
        Optional<Request> requestFound = query().filter(r -> r.getRequestID().equals(id)).findFirst();
        if (!requestFound.isPresent()) {
            throw new RequestNotFoundException();
        }
        return requestFound.get();
    }

    @Override
    public List<Request> findByResponsibleMail(String email) throws RequestNotFoundException {
        List<Request> requestsFound = query().filter(r -> r.getResponsible().getMailAddress().equals(email)).collect(Collectors.toList());
        if (requestsFound.isEmpty()) {
            throw new RequestNotFoundException();
        }
        return requestsFound;
    }
}