package org.Marv1n.code.Repository;

import org.Marv1n.code.Request;

import java.util.Optional;
import java.util.UUID;

public interface IRequestRepository extends IRepository<Request> {
    Optional<Request> findByUUID(UUID id);
}
