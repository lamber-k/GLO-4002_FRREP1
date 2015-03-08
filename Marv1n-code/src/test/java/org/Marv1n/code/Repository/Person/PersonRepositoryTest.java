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
        personRepository = new PersonRepository();
        personUUID = UUID.randomUUID();
    }

    @Test
    public void emptyRepositoryWhenCreateEntryThenFindByUUIDReturnSamePerson() throws Exception {
        when(mockPerson.getID()).thenReturn(personUUID);
        personRepository.create(mockPerson);

        Optional<Person> result = personRepository.findByUUID(personUUID);

        assertTrue(result.isPresent());
        assertEquals(mockPerson, result.get());
    }

    @Test
    public void repositoryNotEmptyWhenFindWithListOfUUIDThenReturnMatchesUUIDPerson() throws Exception {
        putSomeItemsInRepository();
        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(personUUID);
        listOfUUID.add(anotherMockPersonUUID);

        List<Person> results = personRepository.findByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(personUUID)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(anotherMockPersonUUID)).findAny().isPresent());
    }

    private void putSomeItemsInRepository() {
        when(mockPerson.getID()).thenReturn(personUUID);
        anotherMockPersonUUID = UUID.randomUUID();
        when(anotherMockPerson.getID()).thenReturn(anotherMockPersonUUID);
        personRepository.create(mockPerson);
        personRepository.create(anotherMockPerson);
    }
}
