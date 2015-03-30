package infrastructure.persistence;

import org.Marv1n.core.person.Person;
import org.Marv1n.core.person.PersonNotFoundException;
import org.Marv1n.core.person.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonRepositoryInMemory extends RepositoryInMemory<Person> implements PersonRepository {

    @Override
    public Person findByUUID(UUID id) throws PersonNotFoundException {
        Optional<Person> personFound = query().filter(p -> p.getID().equals(id)).findFirst();
        if (!personFound.isPresent()) {
            throw new PersonNotFoundException();
        }
        return personFound.get();
    }

    @Override
    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        return query().filter(p -> listOfUUID.contains(p.getID())).collect(Collectors.toList());
    }

    @Override
    public Person findByEmail(String email) throws PersonNotFoundException {
        Optional<Person> personFound = query().filter(p -> p.getMailAddress().equals(email)).findFirst();
        if (!personFound.isPresent()) {
            throw new PersonNotFoundException();
        }
        return personFound.get();
    }

    @Override
    public List<Person> findAdmins() {
        return query().filter(Person::isAdmin).collect(Collectors.toList());
    }
}
