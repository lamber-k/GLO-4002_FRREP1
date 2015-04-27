package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
    private static final String A_CHANGE_REASON = "This is a change reason";
    private static final RequestStatus REQUEST_STATUS = RequestStatus.ACCEPTED;
    private static final UUID REQUEST_UUID = UUID.randomUUID();
    private MailNotificationFactory mailFactory;
    @Mock
    private Mail mailMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private Person personMock;
    @Mock
    private Person anotherPersonMock;
    @Mock
    private Person responsiblePersonMock;
    @Mock
    private EmailValidator emailValidatorMock;
    @Mock
    private Request requestMock;

    @Before
    public void initializeMailFactory() {
        mailFactory = new MailNotificationFactory(mailSenderMock, emailValidatorMock);
        when(emailValidatorMock.validateMailAddress(anyString())).thenReturn(true);
        when(personMock.isAdmin()).thenReturn(false);
        when(personMock.getMailAddress()).thenReturn(PERSON_ADDRESS);
        when(anotherPersonMock.isAdmin()).thenReturn(false);
        when(anotherPersonMock.getMailAddress()).thenReturn(ANOTHER_PERSON_ADDRESS);
        initializeRequestBehavior();
    }

    private void initializeRequestBehavior() {
        when(requestMock.getResponsible()).thenReturn(responsiblePersonMock);
        when(requestMock.getRequestStatus()).thenReturn(REQUEST_STATUS);
        when(requestMock.getPriority()).thenReturn(PRIORITY);
        when(requestMock.getNumberOfSeatsNeeded()).thenReturn(NUMBER_OF_SEAT);
        when(requestMock.getParticipants()).thenReturn(Arrays.asList(personMock, anotherPersonMock));
        when(requestMock.getRequestID()).thenReturn(REQUEST_UUID);
        when(requestMock.getReason()).thenReturn(A_CHANGE_REASON);

    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenCreatedNotificationShouldHaveToMails() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(PERSON_ADDRESS, ANOTHER_PERSON_ADDRESS));
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenMailNotificationMessageShouldContainRequestStatusChangeReason() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.getMailToSend().getMessage().toString().contains(A_CHANGE_REASON.toString()));
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenMailNotificationMessageShouldContainRequestStatus() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.getMailToSend().getMessage().toString().contains(REQUEST_STATUS.toString()));
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenMailNotificationMessageShouldContainRequestID() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.getMailToSend().getMessage().toString().contains(REQUEST_UUID.toString()));
    }

    @Test(expected = InvalidNotificationException.class)
    public void givenMailFactory_WhenCreateNotificationWithInvalidNotificationInfo_ThenShouldThrowInvalidNotification() throws InvalidNotificationException {
        Request request = new Request();

        mailFactory.createNotification(request);
    }

    @Test
    public void givenMailFactoryWithForwardedEmail_WhenCreateNotification_ThenCreatedNotificationShouldHaveForwardedEmail() {
        mailFactory.addForwardEmail(FORWARDED_EMAIL);
        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(FORWARDED_EMAIL));
    }

    @Test(expected = InvalidMailAddressException.class)
    public void givenMailFactory_WhenAddInvalidMailAddress_ThenShouldThrowInvalidMailAddress() {
        when(emailValidatorMock.validateMailAddress(WRONG_FORWARDED_EMAIL)).thenReturn(false);
        mailFactory.addForwardEmail(WRONG_FORWARDED_EMAIL);
    }
}
