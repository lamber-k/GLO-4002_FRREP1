package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonInvalidFormatException;
import ca.ulaval.glo4002.core.person.PersonNotFoundException;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.persistence.hibernate.EntityManagerProvider;

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
        return null;
    }

    @Override
    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        return null;
    }

    @Override
    public Person findByEmail(String email) throws PersonNotFoundException {
        return null;
    }

    @Override
    public List<Person> findAdmins() {
        return null;
    }
}
