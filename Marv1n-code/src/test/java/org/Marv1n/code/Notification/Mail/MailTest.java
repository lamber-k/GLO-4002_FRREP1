package org.Marv1n.code.Notification.Mail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MailTest {

    private static final String TO_MAIL = "TO@exemple.com";
    private static final String FROM_MAIL = "from@exemple.com";
    private static final String ANOTHER_FROM_MAIL = "FromDifferentSender@exemple.com";
    private static final String ANOTHER_TO_MAIL = "TODifferentDestination@exemple.com";
    private static final String A_SUBJECT = "Subject";
    private static final String A_MESSAGE = "Message";
    private static final String DIFFERENT_SUBJECT = "DifferentSubject";
    private static final String DIFFERENT_MESSAGE = "DifferentMessage";
    private Mail mail;
    private Mail differentMail;

    @Before
    public void initializeMail() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        mail = new Mail(FROM_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingTheSameValueForAllVariable_ThenReturnTrue() {
        assertTrue(mail.equals(mail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentFrom_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        differentMail = new Mail(ANOTHER_FROM_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentTo_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(ANOTHER_TO_MAIL);
        differentMail = new Mail(FROM_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentAmountOfDestinations_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        destinationMail.add(ANOTHER_TO_MAIL);
        differentMail = new Mail(FROM_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubject_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        differentMail = new Mail(FROM_MAIL, destinationMail, DIFFERENT_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubjectMessage_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        differentMail = new Mail(FROM_MAIL, destinationMail, A_SUBJECT, DIFFERENT_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToDifferentObjectType_ThenReturnFalse() {
        Integer differentObject = 25;

        assertFalse(mail.equals(differentObject));
    }

    @Test
    public void givenAMail_WhenComparedToNull_ThenReturnFalse() {
        assertFalse(mail.equals(null));
    }
}