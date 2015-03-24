package Persistence;

import org.Marv1n.core.Person.Person;
import org.Marv1n.core.Person.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonRepositoryInMemory extends RepositoryInMemory<Person> implements PersonRepository {

    @Override
    public Optional<Person> findByUUID(UUID id) {
        return query().filter(p -> p.getID().equals(id)).findFirst();
    }

    @Override
    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        return query().filter(p -> listOfUUID.contains(p.getID())).collect(Collectors.toList());
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return query().filter(p -> p.getMailAddress().equals(email)).findFirst();
    }

    @Override
    public List<Person> findAdmins() {
        return query().filter(Person::isAdmin).collect(Collectors.toList());
    }
}
