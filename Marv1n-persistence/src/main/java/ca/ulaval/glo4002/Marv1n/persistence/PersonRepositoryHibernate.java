package ca.ulaval.glo4002.Marv1n.persistence;

import ca.ulaval.glo4002.Marv1n.persistence.hibernate.EntityManagerProvider;
import core.notification.mail.EmailValidator;
import core.persistence.InvalidFormatException;
import core.person.Person;
import core.person.PersonInvalidFormatException;
import core.person.PersonNotFoundException;
import core.person.PersonRepository;

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
    public Person findByUUID(UUID id) throws PersonNotFoundException {
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
