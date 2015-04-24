package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.persistence.Repository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends Repository<Request> {

    void persist(Request request);

    Request findByUUID(UUID id) throws RequestNotFoundException;

    List<Request> findByResponsibleMail(String mail) throws RequestNotFoundException;
}
