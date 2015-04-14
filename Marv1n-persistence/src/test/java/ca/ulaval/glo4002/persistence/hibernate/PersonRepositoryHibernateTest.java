package ca.ulaval.glo4002.persistence.hibernate;

import ca.ulaval.glo4002.core.notification.mail.EmailValidator;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRepositoryHibernateTest {

    private static final String AN_ADMIN_VALID_EMAIL = "admin.valid@mail.com";
    private static final String ANOTHER_ADMIN_VALID_EMAIL = "another.admin.valid@mail.com";
    private static final String UNKNOWN_EMAIL_ADDRESS = "unknown@mail.com";
    private static final String A_VALID_EMAIL = "valid@mail.com";
    private static final String ANOTHER_VALID_EMAIL = "another.valid@mail.com";
    private static final String AN_INVALID_EMAIL = "invalidmail";
    private EntityManagerFactory entityManagerFactory;
    private PersonRepositoryHibernate personRepository;
    private Person person;
    private Person anotherPerson;
    private Person personAdmin;
    private Person anotherPersonAdmin;
    private EmailValidator emailValidatorMock;

    @Before
    public void initializePersonRepository() throws InvalidFormatException {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());
        emailValidatorMock = mock(EmailValidator.class);
        when(emailValidatorMock.validateMailAddress(A_VALID_EMAIL)).thenReturn(true);
        when(emailValidatorMock.validateMailAddress(ANOTHER_VALID_EMAIL)).thenReturn(true);
        when(emailValidatorMock.validateMailAddress(AN_INVALID_EMAIL)).thenReturn(false);
        when(emailValidatorMock.validateMailAddress(AN_ADMIN_VALID_EMAIL)).thenReturn(true);
        when(emailValidatorMock.validateMailAddress(ANOTHER_ADMIN_VALID_EMAIL)).thenReturn(true);
        personRepository = new PersonRepositoryHibernate(emailValidatorMock);
        populateDB();
    }

    private void populateDB() throws InvalidFormatException {
        person = new Person(A_VALID_EMAIL);
        anotherPerson = new Person(ANOTHER_VALID_EMAIL);
        personAdmin = new Person(AN_ADMIN_VALID_EMAIL, true);
        anotherPersonAdmin = new Person(ANOTHER_ADMIN_VALID_EMAIL, true);
        personRepository.persist(person);
        personRepository.persist(anotherPerson);
        personRepository.persist(personAdmin);
        personRepository.persist(anotherPersonAdmin);
    }

    @Test
    public void givenEmptyPersonRepositoryHibernate_WhenCreateEntry_ThenFindByUUIDReturnSamePerson() throws Exception {
        Person personFound = personRepository.findByUUID(person.getID());
        assertEquals(person, personFound);
    }

    @Test
    public void givenNotEmptyPersonRepositoryHibernate_WhenFindWithListOfUUID_ThenReturnMatchesUUIDPerson() throws Exception {
        List<UUID> listOfUUID = new LinkedList<>();
        listOfUUID.add(person.getID());
        listOfUUID.add(anotherPerson.getID());
        List<Person> results = personRepository.findByListOfUUID(listOfUUID);

        assertThat(results, containsInAnyOrder(person, anotherPerson));
    }

    @Test(expected = PersonNotFoundException.class)
    public void givenEmptyPersonRepositoryHibernate_WhenFindByEmail_ThenThrowPersonNotFound() throws PersonNotFoundException {
        personRepository.findByEmail(UNKNOWN_EMAIL_ADDRESS);
    }

    @Test(expected = PersonNotFoundException.class)
    public void givenNotEmptyPersonRepositoryHibernate_WhenFindByEmailWithWrongEmail_ThenThrowPersonNotFound() throws PersonNotFoundException, InvalidFormatException {
        personRepository.findByEmail(AN_INVALID_EMAIL);
    }

    @Test
    public void givenNotEmptyPersonRepositoryHibernate_WhenFindByEmailWithCorrectEmail_ThenReturnResultWithCorrespondingElement() throws PersonNotFoundException, InvalidFormatException {
        Person personFound = personRepository.findByEmail(A_VALID_EMAIL);

        assertEquals(person, personFound);
    }

    @Test
    public void givenNotEmptyPersonRepositoryHibernateWithAdmin_WhenFindAdmins_ThenShouldReturnAllAdmins() throws InvalidFormatException {
        List<Person> adminsReturned = personRepository.findAdmins();

        List<Person> expectedAdminList = Arrays.asList(personAdmin, anotherPersonAdmin);
        assertEquals(expectedAdminList, adminsReturned);
    }

    @After
    public void clearPersonTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Person r").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}