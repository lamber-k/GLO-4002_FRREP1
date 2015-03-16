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

    private static String EMAIL_VALID = "exemple@exemple.com";
    private static String EMAIL_WRONG = "exemple2@exemple.com";
    private PersonRepositoryInMemory personRepository;
    private UUID personUUID;
    private UUID anotherPersonUUID;
    @Mock
    private Person personMock;
    @Mock
    private Person anotherPersonMock;

    @Before
    public void initializePersonRepositoryInMemory() throws Exception {
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
    public void givenNotEmptyPersonRepository_WhenFindWithListOfUUID_ThenReturnMatchesUUIDPerson() throws Exception {
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
        Optional<Person> result = personRepository.findByEmail(EMAIL_VALID);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenNotEmptyPersonRepository_WhenFindByEmailWithWrongEmail_ThenReturnEmptyResult() {
        when(personMock.getMailAddress()).thenReturn(EMAIL_VALID);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(EMAIL_WRONG);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenNotEmptyPersonRepository_WhenFindByEmailWithCorrectEmail_ThenReturnResultWithCorrespondingElement() {
        when(personMock.getMailAddress()).thenReturn(EMAIL_VALID);
        personRepository.create(personMock);

        Optional<Person> result = personRepository.findByEmail(EMAIL_VALID);

        assertTrue(result.isPresent());
    }


    @Test
    public void givenNotEmptyPersonRepositoryWithAdmin_WhenFindAdmins_ThenShouldReturnAllAdmins() {
        putSomeItemsInRepository();
        Person personAdmin1Mock = mock(Person.class);
        Person personAdmin2Mock = mock(Person.class);
        when(personAdmin1Mock.isAdmin()).thenReturn(true);
        when(personAdmin2Mock.isAdmin()).thenReturn(true);
        personRepository.create(personAdmin1Mock);
        personRepository.create(personAdmin2Mock);

        List<Person> adminsReturned = personRepository.findAdmins();

        List<Person> expectedAdminList = Arrays.asList(personAdmin1Mock, personAdmin2Mock);
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
