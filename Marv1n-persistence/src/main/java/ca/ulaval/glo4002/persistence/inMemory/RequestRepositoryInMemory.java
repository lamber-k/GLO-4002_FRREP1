package ca.ulaval.glo4002.persistence.inMemory;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;

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
    public List<Request> findAllPendingRequest() {
        return query().filter(p -> p.getRequestStatus() == RequestStatus.PENDING).collect(Collectors.toList());
    }
}