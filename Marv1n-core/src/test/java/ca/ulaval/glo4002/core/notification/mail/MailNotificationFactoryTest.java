package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.notification.InvalidNotificationException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.person.PersonRepository;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationFactoryTest {

    private static final String PERSON_ADDRESS = "person@address.com";
    private static final String ANOTHER_PERSON_ADDRESS = "another.person@address.com";
    private static final String ADMIN_ADDRESS = "admin@address.com";
    private static final int NUMBER_OF_SEAT = 42;
    private static final int PRIORITY = 1;
    @Mock
    private Mail mailMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private PersonRepository personRepositoryMock;
    @Mock
    private Person personMock;
    @Mock
    private Person anotherPersonMock;
    @Mock
    private Person adminMock;
    private MailNotificationFactory mailFactory;
    @Mock
    private ca.ulaval.glo4002.core.person.Person person;

    @Before
    public void initializeMailFactory() {
        mailFactory = new MailNotificationFactory(mailSenderMock, personRepositoryMock);
        when(personMock.isAdmin()).thenReturn(false);
        when(personMock.getMailAddress()).thenReturn(PERSON_ADDRESS);
        when(anotherPersonMock.isAdmin()).thenReturn(false);
        when(anotherPersonMock.getMailAddress()).thenReturn(ANOTHER_PERSON_ADDRESS);
        when(adminMock.isAdmin()).thenReturn(true);
        when(adminMock.getMailAddress()).thenReturn(ADMIN_ADDRESS);
        when(personRepositoryMock.findAdmins()).thenReturn(Arrays.asList(adminMock));
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

}
