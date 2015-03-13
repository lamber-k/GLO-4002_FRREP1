package org.Marv1n.code.Repository.Person;

import org.Marv1n.code.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRepositoryTest {

    private static String AN_EMAIL = "exemple@exemple.com";
    private static String AN_OTHER_EMAIL = "exemple2@exemple.com";
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

    @Test
    public void givenEmptyRepository_whenFindByEmail_thenReturnEmptyResult() {
        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenRepositoryContainingNonCorrespondingElement_whenFindByEmail_thenReturnEmptyResult() {
        when(mockPerson.getMailAddress()).thenReturn(AN_OTHER_EMAIL);
        personRepository.create(mockPerson);
        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenRepositoryContainingCorrespondingElement_whenFindByEmail_thenReturnResultWithCorrespondingElement() {
        when(mockPerson.getMailAddress()).thenReturn(AN_EMAIL);
        personRepository.create(mockPerson);
        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertTrue(result.isPresent());
    }


    @Test
    public void givenRepositoryWithAdmin_whenFindAdmins_shouldReturnAllAdmins() {
        putSomeItemsInRepository();
        Person mockPersonAdmin1 = mock(Person.class);
        Person mockPersonAdmin2 = mock(Person.class);
        when(mockPersonAdmin1.isAdmin()).thenReturn(true);
        when(mockPersonAdmin2.isAdmin()).thenReturn(true);
        personRepository.create(mockPersonAdmin1);
        personRepository.create(mockPersonAdmin2);
        List<Person> expectedAdminList = Arrays.asList(mockPersonAdmin1, mockPersonAdmin2);

        List<Person> adminsReturned = personRepository.findAdmins();

        assertEquals(expectedAdminList, adminsReturned);
    }

    private void putSomeItemsInRepository() {
        when(mockPerson.getID()).thenReturn(personUUID);
        anotherMockPersonUUID = UUID.randomUUID();
        when(anotherMockPerson.getID()).thenReturn(anotherMockPersonUUID);
        personRepository.create(mockPerson);
        personRepository.create(anotherMockPerson);
    }
}
