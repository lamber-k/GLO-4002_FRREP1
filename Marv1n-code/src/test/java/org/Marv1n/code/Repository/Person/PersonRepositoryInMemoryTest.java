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
public class PersonRepositoryInMemoryTest {

    private static String EMAIL = "exemple@exemple.com";
    private static String ANOTHER_EMAIL = "exemple2@exemple.com";
    private PersonRepositoryInMemory personRepository;
    @Mock
    private Person personMock;
    private UUID personUUID;
    @Mock
    private Person anotherPersonMock;
    private UUID anotherPersonUUID;

    @Before
    public void setUp() throws Exception {
        personRepository = new PersonRepositoryInMemory();
        personUUID = UUID.randomUUID();
    }

    @Test
    public void givenEmptyPersonRepository_WhenCreateEntry_ThenFindByUUIDReturnSamePerson() throws Exception {
        when(personMock.getID()).thenReturn(personUUID);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByUUID(personUUID);
        assertTrue(result.isPresent());
        assertEquals(personMock, result.get());
    }

    @Test
    public void givenPersonRepositoryNotEmpty_WhenFindWithListOfUUID_ThenReturnMatchesUUIDPerson() throws Exception {
        putSomeItemsInRepository();

        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(personUUID);
        listOfUUID.add(anotherPersonUUID);
        List<Person> results = personRepository.findByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(personUUID)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(anotherPersonUUID)).findAny().isPresent());
    }

    @Test
    public void givenEmptyPersonRepository_WhenFindByEmail_ThenReturnEmptyResult() {
        Optional<Person> result = personRepository.findByEmail(EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenPersonRepositoryContainingNonCorrespondingElement_WhenFindByEmail_ThenReturnEmptyResult() {
        when(personMock.getMailAddress()).thenReturn(ANOTHER_EMAIL);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenPersonRepositoryContainingCorrespondingElement_WhenFindByEmail_ThenReturnResultWithCorrespondingElement() {
        when(personMock.getMailAddress()).thenReturn(EMAIL);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(EMAIL);

        assertTrue(result.isPresent());
    }


    @Test
    public void givenPersonRepositoryWithAdmin_WhenFindAdmins_ThenShouldReturnAllAdmins() {
        putSomeItemsInRepository();
        Person mockPersonAdmin1 = mock(Person.class);
        Person mockPersonAdmin2 = mock(Person.class);
        when(mockPersonAdmin1.isAdmin()).thenReturn(true);
        when(mockPersonAdmin2.isAdmin()).thenReturn(true);
        personRepository.create(mockPersonAdmin1);
        personRepository.create(mockPersonAdmin2);

        List<Person> adminsReturned = personRepository.findAdmins();

        List<Person> expectedAdminList = Arrays.asList(mockPersonAdmin1, mockPersonAdmin2);
        assertEquals(expectedAdminList, adminsReturned);
    }

    private void putSomeItemsInRepository() {
        when(personMock.getID()).thenReturn(personUUID);
        anotherPersonUUID = UUID.randomUUID();
        when(anotherPersonMock.getID()).thenReturn(anotherPersonUUID);
        personRepository.create(personMock);
        personRepository.create(anotherPersonMock);
    }
}
