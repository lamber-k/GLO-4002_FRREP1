package ca.ulaval.glo4002.core.person;

import ca.ulaval.glo4002.core.persistence.Repository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends Repository<Person> {

    Person findByUUID(UUID id) throws PersonNotFoundException;

    List<Person> findByListOfUUID(List<UUID> listOfUUID);

    Person findByEmail(String email) throws PersonNotFoundException;

    List<Person> findAdmins();
}
