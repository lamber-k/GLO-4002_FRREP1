package org.Marv1n.core.persistence;

import org.Marv1n.core.persistence.Repository;
import org.Marv1n.core.request.Request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends Repository<Request> {

    Optional<Request> findByUUID(UUID id);

    List<Request> findAllPendingRequest();
}
