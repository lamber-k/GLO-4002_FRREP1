package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRepositoryTest {

    private PersonRepository personRepository;
    @Mock
    private Person mockPerson;
    private UUID personUUID;
    @Mock
    private Person anotherMockPerson;
    private UUID anotherMockPersonUUID;

    @Before
    public void setUp() throws Exception {
        this.personRepository = new PersonRepository();
        this.personUUID = UUID.randomUUID();
    }

    @Test
    public void emptyRepositoryWhenCreateEntryThenFindByUUIDReturnSamePerson() throws Exception {
        when(this.mockPerson.getID()).thenReturn(this.personUUID);
        this.personRepository.create(this.mockPerson);

        Optional<Person> result = this.personRepository.findByUUID(this.personUUID);

        assertTrue(result.isPresent());
        assertEquals(this.mockPerson, result.get());
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
        when(this.mockPerson.getID()).thenReturn(this.personUUID);
        this.anotherMockPersonUUID = UUID.randomUUID();
        when(this.anotherMockPerson.getID()).thenReturn(this.anotherMockPersonUUID);
        this.personRepository.create(this.mockPerson);
        this.personRepository.create(this.anotherMockPerson);
    }
}
