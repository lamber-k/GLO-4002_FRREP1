package infrastructure.persistence;

import core.person.Person;
import core.person.PersonNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRepositoryInMemoryTest {

    private static String A_VALID_EMAIL = "exemple@exemple.com";
    private static String A_WRONG_EMAIL = "exemple2@exemple.com";
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
    public void givenEmptyPersonRepositoryInMemory_WhenCreateEntry_ThenFindByUUIDReturnSamePerson() throws Exception {
        when(personMock.getID()).thenReturn(personUUID);
        personRepository.persist(personMock);

        Person personFound = personRepository.findByUUID(personUUID);
        assertEquals(personMock, personFound);
    }

    @Test
    public void givenNotEmptyPersonRepositoryInMemory_WhenFindWithListOfUUID_ThenReturnMatchesUUIDPerson() throws Exception {
        putSomeItemsInRepository();

        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(personUUID);
        listOfUUID.add(anotherPersonUUID);
        List<Person> results = personRepository.findByListOfUUID(listOfUUID);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().filter(r -> r.getID().equals(personUUID)).findAny().isPresent());
        assertTrue(results.stream().filter(r -> r.getID().equals(anotherPersonUUID)).findAny().isPresent());
    }

    @Test(expected = PersonNotFoundException.class)
    public void givenEmptyPersonRepositoryInMemory_WhenFindByEmail_ThenThrowPersonNotFound() throws PersonNotFoundException {
        personRepository.findByEmail(A_VALID_EMAIL);
    }

    @Test(expected = PersonNotFoundException.class)
    public void givenNotEmptyPersonRepositoryInMemory_WhenFindByEmailWithWrongEmail_ThenThrowPersonNotFound() throws PersonNotFoundException {
        when(personMock.getMailAddress()).thenReturn(A_VALID_EMAIL);
        personRepository.persist(personMock);

        personRepository.findByEmail(A_WRONG_EMAIL);
    }

    @Test
    public void givenNotEmptyPersonRepositoryInMemory_WhenFindByEmailWithCorrectEmail_ThenReturnResultWithCorrespondingElement() throws PersonNotFoundException {
        when(personMock.getMailAddress()).thenReturn(A_VALID_EMAIL);
        personRepository.persist(personMock);

        Person personFound = personRepository.findByEmail(A_VALID_EMAIL);

        assertEquals(personMock, personFound);
    }

    @Test
    public void givenNotEmptyPersonRepositoryInMemoryWithAdmin_WhenFindAdmins_ThenShouldReturnAllAdmins() {
        putSomeItemsInRepository();
        Person personAdmin1Mock = mock(Person.class);
        Person personAdmin2Mock = mock(Person.class);
        when(personAdmin1Mock.isAdmin()).thenReturn(true);
        when(personAdmin2Mock.isAdmin()).thenReturn(true);
        personRepository.persist(personAdmin1Mock);
        personRepository.persist(personAdmin2Mock);

        List<Person> adminsReturned = personRepository.findAdmins();

        List<Person> expectedAdminList = Arrays.asList(personAdmin1Mock, personAdmin2Mock);
        assertEquals(expectedAdminList, adminsReturned);
    }

    private void putSomeItemsInRepository() {
        when(personMock.getID()).thenReturn(personUUID);
        anotherPersonUUID = UUID.randomUUID();
        when(anotherPersonMock.getID()).thenReturn(anotherPersonUUID);
        personRepository.persist(personMock);
        personRepository.persist(anotherPersonMock);
    }
}