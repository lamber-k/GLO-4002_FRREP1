package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.Repository.IRepository;
import org.Marv1n.code.Request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRequestRepository extends IRepository<Request> {
    Optional<Request> findByUUID(UUID id);

    List<Request> findAllPendingRequest();

}
