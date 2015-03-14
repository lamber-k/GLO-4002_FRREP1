package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.RepositoryInMemory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonRepositoryInMemory extends RepositoryInMemory<Person> implements IPersonRepository {

    public Optional<Person> findByUUID(UUID id) {
        return query().filter(p -> p.getID().equals(id)).findFirst();
    }

    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        return query().filter(p -> listOfUUID.contains(p.getID())).collect(Collectors.toList());
    }

    public Optional<Person> findByEmail(String email) {
        return query().filter(p -> p.getMailAddress().equals(email)).findFirst();
    }

    public List<Person> findAdmins() {
        return query().filter(p -> p.isAdmin()).collect(Collectors.toList());
    }
}
