package infrastructure.persistence;

import core.request.Request;
import core.request.RequestRepository;
import core.request.RequestStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestRepositoryInMemory extends RepositoryInMemory<Request> implements RequestRepository {

    @Override
    public Optional<Request> findByUUID(UUID id) {
        return query().filter(r -> r.getRequestID().equals(id)).findFirst();
    }

    @Override
    public List<Request> findAllPendingRequest() {
        return query().filter(p -> p.getRequestStatus() == RequestStatus.PENDING).collect(Collectors.toList());
    }
}