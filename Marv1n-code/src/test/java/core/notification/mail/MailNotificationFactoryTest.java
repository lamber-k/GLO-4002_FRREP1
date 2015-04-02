package core.notification.mail;

import core.notification.InvalidNotificationException;
import core.notification.NotificationInfo;
import core.person.Person;
import core.person.PersonRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationFactoryTest {

    private static final String PERSON_ADDRESS = "person@address.com";
    private static final String ANOTHER_PERSON_ADDRESS = "another.person@address.com";
    private static final String ADMIN_ADDRESS = "admin@address.com";
    @Mock
    private Mail mailMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private PersonRepository personRepositoryMock;
    @Mock
    private NotificationInfo notificationInfo;
    @Mock
    private Person personMock;
    @Mock
    private Person anotherPersonMock;
    @Mock
    private Person adminMock;
    private MailNotificationFactory mailFactory;

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
        notificationInfo = new NotificationInfo("category", "status", "identifier", "detail", Arrays.asList(personMock, anotherPersonMock));
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenCreatedNotificationShouldHaveToMails() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(notificationInfo);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(PERSON_ADDRESS, ANOTHER_PERSON_ADDRESS));
    }
}
