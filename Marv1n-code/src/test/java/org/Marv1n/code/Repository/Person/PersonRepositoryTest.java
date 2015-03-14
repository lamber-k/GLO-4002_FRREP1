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
    private Person personMock;
    private UUID personUUIDMock;
    @Mock
    private Person anotherPersonMock;
    private UUID anotherPersonUUIDMock;

    @Before
    public void setUp() throws Exception {
        personRepository = new PersonRepository();
        personUUIDMock = UUID.randomUUID();
    }

    @Test
    public void emptyRepository_WhenCreateEntry_ThenFindByUUIDReturnSamePerson() throws Exception {
        when(personMock.getID()).thenReturn(personUUIDMock);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByUUID(personUUIDMock);

        assertTrue(result.isPresent());
        assertEquals(personMock, result.get());
    }

    @Test
    public void repositoryNotEmpty_WhenFindWithListOfUUID_ThenReturnMatchesUUIDPerson() throws Exception {
        putSomeItemsInRepository();
        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(personUUIDMock);
        listOfUUID.add(anotherPersonUUIDMock);

        List<Person> results = personRepository.findByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(personUUIDMock)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(anotherPersonUUIDMock)).findAny().isPresent());
    }

    @Test
    public void givenEmptyRepository_whenFindByEmail_thenReturnEmptyResult() {
        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenRepositoryContainingNonCorrespondingElement_whenFindByEmail_thenReturnEmptyResult() {
        when(personMock.getMailAddress()).thenReturn(AN_OTHER_EMAIL);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenRepositoryContainingCorrespondingElement_WhenFindByEmail_ThenReturnResultWithCorrespondingElement() {
        when(personMock.getMailAddress()).thenReturn(AN_EMAIL);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(AN_EMAIL);

        assertTrue(result.isPresent());
    }


    @Test
    public void givenRepositoryWithAdmin_WhenFindAdmins_ThenShouldReturnAllAdmins() {
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
        when(personMock.getID()).thenReturn(personUUIDMock);
        anotherPersonUUIDMock = UUID.randomUUID();
        when(anotherPersonMock.getID()).thenReturn(anotherPersonUUIDMock);
        personRepository.create(personMock);
        personRepository.create(anotherPersonMock);
    }
}
