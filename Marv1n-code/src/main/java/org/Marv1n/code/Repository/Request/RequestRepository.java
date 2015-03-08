package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Request;

import java.util.Optional;
import java.util.UUID;

public class RequestRepository extends Repository<Request> implements IRequestRepository {

    public Optional<Request> findByUUID(UUID id) {
        return query().filter(r -> r.getRequestID().equals(id)).findFirst();
    }
}
