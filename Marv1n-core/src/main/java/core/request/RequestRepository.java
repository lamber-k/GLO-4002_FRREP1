package core.request;

import core.persistence.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends Repository<Request> {

    void persist(Request request) throws InvalidRequestFormatException;

    Optional<Request> findByUUID(UUID id);

    List<Request> findAllPendingRequest();
}
