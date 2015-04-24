package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

public class RequestRepositoryHibernate extends RepositoryHibernate<Request> implements RequestRepository {

    public RequestRepositoryHibernate() {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public Request findByUUID(UUID requestID) throws RequestNotFoundException {
        try {
            Query query = entityManager.createQuery("select r from Request r where r.requestID = :requestUUID");
            query.setParameter("requestUUID", requestID);
            return (Request) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new RequestNotFoundException(exception);
        }
    }

    @Override
    public List<Request> findByResponsibleMail(String mail) throws RequestNotFoundException {
        try {
            Query query = entityManager.createQuery("select r from Request r where r.responsible.email = :mail");
            query.setParameter("mail", mail);
            return (List<Request>) query.getResultList();
        } catch (NoResultException e) {
            throw new RequestNotFoundException(e);
        }
    }
}
