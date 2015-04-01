package infrastructure.persistence;

import core.person.PersonNotFoundException;
import core.person.PersonRepository;
import infrastructure.hibernate.EntityManagerProvider;

import java.util.List;
import java.util.UUID;

public class PersonRepositoryHibernate extends RepositoryHibernate<core.person.Person> implements PersonRepository {
    public PersonRepositoryHibernate() {
        super(new EntityManagerProvider().getEntityManager());
    }

    @Override
    public core.person.Person findByUUID(UUID id) throws PersonNotFoundException {
        return null;
    }

    @Override
    public List<core.person.Person> findByListOfUUID(List<UUID> listOfUUID) {
        return null;
    }

    @Override
    public core.person.Person findByEmail(String email) throws PersonNotFoundException {
        return null;
    }

    @Override
    public List<core.person.Person> findAdmins() {
        return null;
    }
}
