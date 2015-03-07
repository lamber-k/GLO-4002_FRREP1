package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonRepository extends Repository<Person> implements IPersonRepository {
    public Optional<Person> findByUUID(UUID id) {
        return this.query().filter(p -> p.getID().equals(id)).findFirst();
    }

    public List<Person> findByListOfUUID(List<UUID> listOfUUID) {
        return this.query().filter(p -> listOfUUID.contains(p.getID())).collect(Collectors.toList());
    }
}
