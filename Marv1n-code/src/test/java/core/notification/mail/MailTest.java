package core.notification.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
    @Mock
    private MailAddress toMailMock;
    @Mock
    private MailAddress fromMailMock;
    @Mock
    private MailAddress anotherToMailMock;
    @Mock
    private MailAddress anotherFromMailMock;

    private Mail mail;
    private Mail differentMail;

    @Before
    public void initializeMail() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(toMailMock);
        mail = new Mail(fromMailMock, destinationMail, A_SUBJECT, A_MESSAGE);
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingTheSameValueForAllVariable_ThenReturnTrue() {
        assertTrue(mail.equals(mail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentFrom_ThenReturnFalse() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(toMailMock);
        differentMail = new Mail(anotherFromMailMock, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentTo_ThenReturnFalse() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(anotherToMailMock);
        differentMail = new Mail(fromMailMock, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentAmountOfDestinations_ThenReturnFalse() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(toMailMock);
        destinationMail.add(anotherToMailMock);
        differentMail = new Mail(fromMailMock, destinationMail, A_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubject_ThenReturnFalse() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(toMailMock);
        differentMail = new Mail(fromMailMock, destinationMail, DIFFERENT_SUBJECT, A_MESSAGE);

        assertFalse(mail.equals(differentMail));
    }

    @Test
    public void givenAMail_WhenComparedToMailContainingDifferentSubjectMessage_ThenReturnFalse() {
        List<MailAddress> destinationMail = new ArrayList<>();
        destinationMail.add(toMailMock);
        differentMail = new Mail(fromMailMock, destinationMail, A_SUBJECT, DIFFERENT_MESSAGE);

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