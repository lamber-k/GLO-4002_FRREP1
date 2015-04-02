package core.person;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class PersonTest {

    @Mock
    private static final String MAIL_ADDRESS = "mail@address.com";
    private Person person;

    @Before
    public void initializePerson() {
        person = new Person(MAIL_ADDRESS);
    }

    @Test
    public void givenAdminPerson_WhenTestIfAdmin_ThenReturnTrue() {
        Person adminPerson = new Person(MAIL_ADDRESS, true);

        assertTrue(adminPerson.isAdmin());
    }

    @Test
    public void givenPerson_WhenGetMailAddress_ThenShouldReturnConstructorMail() {
        assertEquals(MAIL_ADDRESS, person.getMailAddress());
    }

    @Test
    public void givenPerson_WhenComparedToIdenticalPerson_ThenReturnTrue() {
        assertTrue(person.equals(person));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentPerson_ThenReturnFalse() {
        Person aDifferentPerson = new Person(MAIL_ADDRESS);
        assertFalse(person.equals(aDifferentPerson));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentObject_ThenReturnFalse() {
        Object aDifferentObject = 25;
        assertFalse(person.equals(aDifferentObject));
    }

    @Test
    public void givenPerson_WhenComparedToNull_ThenReturnFalse() {
        assertFalse(person.equals(null));
    }
}