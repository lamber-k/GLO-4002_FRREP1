package org.Marv1n.code.Notifaction.Mail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MailTest {

    private Mail mail;
    private Mail anotherMail;

    @Before
    public void initializeMail(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TO@exemple.com");
        mail = new Mail("from@exemple.com",destinationMail,"Subject","Message");
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingTheSameValueForAllVariable_ThenReturnTrue(){
        assertTrue(mail.equals(mail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentFrom_ThenReturnFalse(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TO@exemple.com");
        anotherMail = new Mail("FromDifferentSender@exemple.com",destinationMail,"Subject","Message");

        assertFalse(mail.equals(anotherMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentTo_ThenReturnFalse(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TODifferentDestination@exemple.com");
        anotherMail = new Mail("from@exemple.com",destinationMail,"Subject","Message");

        assertFalse(mail.equals(anotherMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentAmountOfDestinations_ThenReturnFalse(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TO@exemple.com");
        destinationMail.add("TODifferentDestination@exemple.com");
        anotherMail = new Mail("from@exemple.com",destinationMail,"Subject","Message");

        assertFalse(mail.equals(anotherMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubject_ThenReturnFalse(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TO@exemple.com");
        anotherMail = new Mail("from@exemple.com",destinationMail,"DifferentSubject","Message");

        assertFalse(mail.equals(anotherMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubjectMessage_ThenReturnFalse(){
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add("TO@exemple.com");
        anotherMail = new Mail("from@exemple.com",destinationMail,"Subject","DifferentMessage");

        assertFalse(mail.equals(anotherMail));
    }

    @Test
    public void givenAMail_WhenComparedToDifferentObjectType_ThenReturnFalse(){
        Integer differentObject = new Integer(25);

        assertFalse(mail.equals(differentObject));
    }

    @Test
    public void givenAMail_WhenComparedToNull_ThenReturnFalse(){
        assertFalse(mail.equals(null));
    }
}