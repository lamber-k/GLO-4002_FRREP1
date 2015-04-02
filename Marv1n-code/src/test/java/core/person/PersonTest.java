package core.person;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.mail.internet.AddressException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonTest {

    @Mock
    private static final String MAIL_ADDRESS = "mail@address.com";
    private Person person;

    @Before
    public void initializePerson() throws AddressException {
        person = new Person(MAIL_ADDRESS);
    }

    @Test
    public void givenPerson_WhenComparedToIdenticalPerson_ThenReturnTrue() {
        assertTrue(person.equals(person));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentPerson_ThenReturnFalse() throws AddressException {
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