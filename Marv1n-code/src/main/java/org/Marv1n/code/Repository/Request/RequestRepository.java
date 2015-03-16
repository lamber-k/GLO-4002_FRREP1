package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.Repository.Repository;
import org.Marv1n.code.Request;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends Repository<Request> {

    Optional<Request> findByUUID(UUID id);

    List<Request> findAllPendingRequest();
}
