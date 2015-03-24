package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonTest {

    private static final String A_EMAIL_ADDRESS = "exemple@exemple.com";
    private Person person;

    @Before
    public void initializePerson() {
        person = new Person(A_EMAIL_ADDRESS);
    }

    @Test
    public void givenPerson_WhenComparedToIdenticalPerson_ThenReturnTrue() {
        assertTrue(person.equals(person));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentPerson_ThenReturnFalse() {
        Person aDifferentPerson = new Person(A_EMAIL_ADDRESS);
        assertFalse(person.equals(aDifferentPerson));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentObject_ThenReturnFalse() {
        Integer aDifferentObject = 25;
        assertFalse(person.equals(aDifferentObject));
    }

    @Test
    public void givenPerson_WhenComparedToNull_ThenReturnFalse() {
        assertFalse(person.equals(null));
    }
}