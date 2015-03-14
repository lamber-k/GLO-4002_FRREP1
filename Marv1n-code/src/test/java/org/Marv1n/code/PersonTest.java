package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    private Person person;
    private Person aDifferentPerson;
    private final String EMAIL_ADRESS = "exemple@exemple.com";

    @Before
    public void init(){
        person = new Person("exemple@exemple.com");
    }

    @Test
    public void givenPerson_WhenComparedToIdenticPerson_ThenReturnTrue(){
        assertTrue(person.equals(person));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentPerson_ThenReturnfalse(){
        aDifferentPerson = new Person(EMAIL_ADRESS);
        assertFalse(person.equals(aDifferentPerson));
    }

    @Test
    public void givenPerson_WhenComparedToDifferentObject_ThenReturnfalse(){
        Integer aDifferentObject = new Integer(25);
        assertFalse(person.equals(aDifferentObject));
    }

    @Test
    public void givenPerson_WhenComparedToNull_ThenReturnfalse(){
        assertFalse(person.equals(null));
    }
}