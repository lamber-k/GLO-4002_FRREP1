package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonRepositoryTest {

    private PersonRepository personRepository;
    private Person mockPerson;
    private UUID personUUID;
    private Person anotherMockPerson;
    private UUID anotherMockPersonUUID;

    @Before
    public void setUp() throws Exception {
        personRepository = new PersonRepository();
        mockPerson = mock(Person.class);
        personUUID = UUID.randomUUID();
    }

    @Test
    public void emptyRepositoryWhenCreateEntryThenFindByUUIDReturnSamePerson() throws Exception {
        when(mockPerson.getID()).thenReturn(this.personUUID);
        this.personRepository.create(mockPerson);

        Optional<Person> result = this.personRepository.findByUUID(this.personUUID);

        assertTrue(result.isPresent());
        assertEquals(mockPerson, result.get());
    }

    @Test
    public void repositoryNotEmptyWhenFindWithListOfUUIDThenReturnMatchesUUIDPerson() throws Exception {
        this.putSomeItemsInRepository();
        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(this.personUUID);
        listOfUUID.add(this.anotherMockPersonUUID);

        List<Person> results = this.personRepository.findByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(this.personUUID)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(this.anotherMockPersonUUID)).findAny().isPresent());
    }

    private void putSomeItemsInRepository() {
        when(mockPerson.getID()).thenReturn(this.personUUID);
        anotherMockPerson = mock(Person.class);
        anotherMockPersonUUID = UUID.randomUUID();
        when(anotherMockPerson.getID()).thenReturn(this.anotherMockPersonUUID);
        this.personRepository.create(mockPerson);
        this.personRepository.create(anotherMockPerson);
    }
}
