package org.Marv1n.code.Repository;

import org.Marv1n.code.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonRepository extends Repository<Person> implements IPersonRepository {
    public Optional<Person> FindByUUID(UUID id) {
        return this.query().filter(p -> p.getID().equals(id)).findFirst();
    }

    public List<Person> FindByListOfUUID(List<UUID> listOfUUID) {
        return this.query().filter(p -> listOfUUID.contains(p.getID())).collect(Collectors.toList());
    }
}
