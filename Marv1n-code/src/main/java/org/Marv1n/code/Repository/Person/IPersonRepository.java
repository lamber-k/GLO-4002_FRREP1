package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPersonRepository extends IRepository<Person> {
    public Optional<Person> findByUUID(UUID id);

    List<Person> findByListOfUUID(List<UUID> listOfUUID);
}
