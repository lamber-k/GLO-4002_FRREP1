package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequestRepository extends Repository<Request> implements IRequestRepository {

    public Optional<Request> findByUUID(UUID id) {
        return query().filter(r -> r.getRequestID().equals(id)).findFirst();
    }

    @Override
    public List<Request> findAllPendingRequest() {
        return this.query().filter(p -> p.getRequestStatus().equals(RequestStatus.PENDING)).collect(Collectors.toList());
    }
}