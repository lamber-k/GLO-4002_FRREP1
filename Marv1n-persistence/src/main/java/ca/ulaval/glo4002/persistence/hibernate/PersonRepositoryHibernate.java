package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonNotFoundException;
import ca.ulaval.glo4002.core.person.PersonRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

public class PersonRepositoryHibernate extends RepositoryHibernate<Person> implements PersonRepository {

    public PersonRepositoryHibernate() {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public void persist(Person person) {
        super.persist(person);
    }

    @Override
    public Person findByUUID(UUID idSearched) throws PersonNotFoundException {
        try {
            Query query = entityManager.createQuery("select p from Person p where p.personID = :idSearched");
            query.setParameter("idSearched", idSearched);
            return (Person) query.getSingleResult();
        } catch (NoResultException exception) {
            throw new PersonNotFoundException(exception);
        }
    }

    @Override
    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        Query query = entityManager.createQuery("select p from Person p where p.personID in :listOfUUID");
        query.setParameter("listOfUUID", listOfUUID);
        return query.getResultList();
    }

    @Override
    public Person findByEmail(String email) throws PersonNotFoundException {
        try {
            Query query = entityManager.createQuery("select p from Person p where p.email = :searchedEmail");
            query.setParameter("searchedEmail", email);
            return (Person) query.getSingleResult();
        } catch (EntityNotFoundException | NoResultException exception) {
            throw new PersonNotFoundException(exception);
        }
    }

    @Override
    public List<Person> findAdmins() {
        Query query = entityManager.createQuery("select p from Person p where p.admin = true");
        return query.getResultList();
    }
}
