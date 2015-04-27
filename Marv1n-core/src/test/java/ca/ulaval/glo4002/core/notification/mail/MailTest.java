package ca.ulaval.glo4002.core.notification.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MailTest {

    private static final String A_SUBJECT = "Subject";
    private static final String A_MESSAGE = "Message";
    private static final String DIFFERENT_SUBJECT = "DifferentSubject";
    private static final String DIFFERENT_MESSAGE = "DifferentMessage";
    private static final String TO_MAIL = "to@mail.com";
    private static final String FROM_MAIL = "from@mail.com";
    private static final String ANOTHER_FROM_MAIL = "another.from@mail.com";
    private static final String ANOTHER_TO_MAIL = "another.to@mail.com";
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
        differentMail = new Mail(ANOTHER_TO_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentTo_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(ANOTHER_FROM_MAIL);
        differentMail = new Mail(FROM_MAIL, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentAmountOfDestinations_ThenReturnFalse() {
        List<String> destinationMail = new ArrayList<>();
        destinationMail.add(TO_MAIL);
        destinationMail.add(ANOTHER_FROM_MAIL);
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
        Object differentObject = 25;

        assertFalse(mail.equals(differentObject));
    }

    @Test
    public void givenAMail_WhenComparedToNull_ThenReturnFalse() {
        assertFalse(mail.equals(null));
    }
}