package org.Marv1n.code;

import org.Marv1n.code.Repository.PersonRepository;
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
    private Person mockPersonTwo;
    private UUID personTwoUUID;

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

        Optional<Person> result = this.personRepository.FindByUUID(this.personUUID);

        assertTrue(result.isPresent());
        assertEquals(mockPerson, result.get());
    }

    private void putSomeItemsInRepository() {
        when(mockPerson.getID()).thenReturn(this.personUUID);
        mockPersonTwo = mock(Person.class);
        personTwoUUID = UUID.randomUUID();
        when(mockPersonTwo.getID()).thenReturn(this.personTwoUUID);
        this.personRepository.create(mockPerson);
        this.personRepository.create(mockPersonTwo);
    }

    @Test
    public void repositoryNotEmptyWhenFindWithListOfUUIDThenReturnMatchesUUIDPerson() throws Exception {
        this.putSomeItemsInRepository();
        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(this.personUUID);
        listOfUUID.add(this.personTwoUUID);

        List<Person> results = this.personRepository.FindByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(this.personUUID)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(this.personTwoUUID)).findAny().isPresent());
    }
}
