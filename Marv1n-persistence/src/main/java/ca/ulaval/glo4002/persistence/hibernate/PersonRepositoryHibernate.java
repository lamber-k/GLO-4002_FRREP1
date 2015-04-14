package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonInvalidFormatException;
import ca.ulaval.glo4002.core.person.PersonNotFoundException;
import ca.ulaval.glo4002.core.person.PersonRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

public class PersonRepositoryHibernate extends RepositoryHibernate<Person> implements PersonRepository {
    private final EmailValidator emailValidator;

    public PersonRepositoryHibernate(EmailValidator emailValidator) {
        super(new EntityManagerProvider().getEntityManager());
        this.emailValidator = emailValidator;
    }

    @Override
    public void persist(Person person) throws InvalidFormatException {
        if (!emailValidator.validateMailAddress(person.getMailAddress())) {
            throw new PersonInvalidFormatException("Invalid mail Address");
        }
        super.persist(person);
    }

    @Override
    public Person findByUUID(UUID idSearched) throws PersonNotFoundException {
        try {
            Query query = entityManager.createQuery("select p from Person p where p.personID = :idSearched");
            query.setParameter("idSearched", idSearched);
            return (Person)query.getSingleResult();
        }
        catch (NoResultException e) {
            throw new PersonNotFoundException();
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
            return (Person)query.getSingleResult();
        }
        catch (EntityNotFoundException | NoResultException exception) {
            throw new PersonNotFoundException(exception);
        }
    }

    @Override
    public List<Person> findAdmins() {
        Query query = entityManager.createQuery("select p from Person p where p.admin = true");
        return query.getResultList();
    }
}
