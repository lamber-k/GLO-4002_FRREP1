package org.Marv1n.code.Repository;

import org.Marv1n.code.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository extends IRepository<Person> {
    public Optional<Person> FindByUUID(UUID id);

    List<Person> FindByListOfUUID(List<UUID> listOfUUID);
}