package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.RequestStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

public class RequestRepositoryHibernate extends RepositoryHibernate<Request> implements RequestRepository {

    public RequestRepositoryHibernate(EntityManager entityManager) {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public Request findByUUID(UUID requestID) throws RequestNotFoundException {
        try {
            return entityManager.getReference(Request.class, requestID);
        }
        catch (EntityNotFoundException exception) {
            throw new RequestNotFoundException(exception);
        }
    }

    @Override
    public List<Request> findAllPendingRequest() {
        return entityManager.createQuery("select r from Request r where r.status = :pending").setParameter("pending", RequestStatus.PENDING).getResultList();
    }
}
