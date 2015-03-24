package org.Marv1n.core.Request;

import org.Marv1n.core.Persistence.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends Repository<Request> {

    Optional<Request> findByUUID(UUID id);

    List<Request> findAllPendingRequest();
}
