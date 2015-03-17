package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends Repository<Person> {

    public Optional<Person> findByUUID(UUID id);

    public List<Person> findByListOfUUID(List<UUID> listOfUUID);

    public Optional<Person> findByEmail(String email);

    public List<Person> findAdmins();
}
