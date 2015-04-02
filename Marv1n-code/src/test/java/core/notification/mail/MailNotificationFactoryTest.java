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

    private MailNotificationFactory mailFactory;
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
    @Mock
    private MailAddress personAddressMock;
    @Mock
    private MailAddress anotherPersonAddressMock;
    @Mock
    private MailAddress adminAddressMock;

    @Before
    public void initializeMailFactory() {
        mailFactory = new MailNotificationFactory(mailSenderMock, personRepositoryMock);
        when(personMock.isAdmin()).thenReturn(false);
        when(personMock.getMailAddress()).thenReturn(personAddressMock);
        when(anotherPersonMock.isAdmin()).thenReturn(false);
        when(anotherPersonMock.getMailAddress()).thenReturn(anotherPersonAddressMock);
        when(adminMock.isAdmin()).thenReturn(true);
        when(adminMock.getMailAddress()).thenReturn(adminAddressMock);
        when(personRepositoryMock.findAdmins()).thenReturn(Arrays.asList(adminMock));
        notificationInfo = new NotificationInfo("category", "status", "identifier", "detail", Arrays.asList(personMock, anotherPersonMock));
    }

    @Test
    public void givenMailFactory_WhenCreateNotification_ThenCreatedNotificationShouldHaveToMails() throws InvalidNotificationException {
        MailNotification returnedNotification = mailFactory.createNotification(notificationInfo);

        assertThat(returnedNotification.getMailToSend().getTo(), CoreMatchers.hasItems(personAddressMock, anotherPersonAddressMock));
    }
}
