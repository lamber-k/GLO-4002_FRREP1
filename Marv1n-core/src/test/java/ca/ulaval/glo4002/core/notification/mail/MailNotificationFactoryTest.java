package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationFactoryTest {

    private static final String PERSON_ADDRESS = "person@address.com";
    private static final String ANOTHER_PERSON_ADDRESS = "another.person@address.com";
    private static final int NUMBER_OF_SEAT = 42;
    private static final int PRIORITY = 1;
    private static final String FORWARDED_EMAIL = "toto@toto42.com";
    private static final String WRONG_FORWARDED_EMAIL = "wrongMail";
    @Mock
    private Mail mailMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private Person personMock;
    @Mock
    private Person anotherPersonMock;
    private MailNotificationFactory mailFactory;
    @Mock
    private Person person;
    @Mock
    private EmailValidator emailValidatorMock;

    @Before
    public void initializeMailFactory() {
        mailFactory = new MailNotificationFactory(mailSenderMock, emailValidatorMock);
        when(emailValidatorMock.validateMailAddress(anyString())).thenReturn(true);
        when(personMock.isAdmin()).thenReturn(false);
        when(personMock.getMailAddress()).thenReturn(PERSON_ADDRESS);
        when(anotherPersonMock.isAdmin()).thenReturn(false);
        when(anotherPersonMock.getMailAddress()).thenReturn(ANOTHER_PERSON_ADDRESS);
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenCreatedNotificationShouldHaveToMails() throws InvalidNotificationException {
        Request request = new Request(NUMBER_OF_SEAT, PRIORITY, person, Arrays.asList(personMock, anotherPersonMock));

        MailNotification returnedNotification = mailFactory.createNotification(request);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(PERSON_ADDRESS, ANOTHER_PERSON_ADDRESS));
    }

    @Test(expected = InvalidNotificationException.class)
    public void givenMailFactory_WhenCreateNotificationWithInvalidNotificationInfo_ThenShouldThrowInvalidNotification() throws InvalidNotificationException {
        Request request = new Request();

        mailFactory.createNotification(request);
    }

    @Test
    public void givenMailFactoryWithForwardedEmail_WhenCreateNotification_ThenCreatedNotificationShouldHaveForwardedEmail() {
        Request request = new Request(NUMBER_OF_SEAT, PRIORITY, person, Arrays.asList(personMock, anotherPersonMock));
        mailFactory.addForwardEmail(FORWARDED_EMAIL);

        MailNotification returnedNotification = mailFactory.createNotification(request);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(PERSON_ADDRESS, ANOTHER_PERSON_ADDRESS));
    }

    @Test(expected = InvalidMailAddressException.class)
    public void givenMailFactory_WhenAddInvalidMailAddress_ThenShouldThrowInvalidMailAddress() {
        when(emailValidatorMock.validateMailAddress(WRONG_FORWARDED_EMAIL)).thenReturn(false);
        mailFactory.addForwardEmail(WRONG_FORWARDED_EMAIL);
    }
}
