package ca.ulaval.glo4002.marv1n.uat.fakes;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.persistence.inmemory.RepositoryInMemory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestRepositoryInMemoryFake<Request> extends RepositoryInMemory<Request> implements RequestRepository {

    @Override
    public void persist(ca.ulaval.glo4002.core.request.Request request) {

    }

    @Override
    public void remove(ca.ulaval.glo4002.core.request.Request object) throws ObjectNotFoundException {

    }

    @Override
    public ca.ulaval.glo4002.core.request.Request findByUUID(UUID id) throws RequestNotFoundException {
        Optional<Request> requestFound = query().filter(r -> r.getRequestID().equals(id)).findFirst();
        if (!requestFound.isPresent()) {
            throw new RequestNotFoundException();
        }
        return (ca.ulaval.glo4002.core.request.Request) requestFound.get();
    }

    @Override
    public List<ca.ulaval.glo4002.core.request.Request> findAllPendingRequest() {
        return query().filter(p -> p.getRequestStatus() == RequestStatus.PENDING).collect(Collectors.toList());
    }
}