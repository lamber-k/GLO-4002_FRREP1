package org.Marv1n.core.notification.mail;

import org.Marv1n.core.notification.InvalidRequestException;
import org.Marv1n.core.person.Person;
import org.Marv1n.core.person.PersonNotFoundException;
import org.Marv1n.core.person.PersonRepository;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestStatus;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomNotFoundException;
import org.Marv1n.core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationFactoryTest {

    private static final UUID RESPONSIBLE_UUID = UUID.randomUUID();
    private static final String RESPONSIBLE_MAIL_ADDRESS = "responsible@test.ca";
    private static final UUID ADMIN_UUID = UUID.randomUUID();
    private static final String ADMIN_MAIL_ADDRESS = "admin@test.ca";
    private static final List<String> TO_ADDRESS = Arrays.asList(RESPONSIBLE_MAIL_ADDRESS, ADMIN_MAIL_ADDRESS);
    private static final UUID REQUEST_UUID = UUID.randomUUID();
    private static final String RESERVABLE_NAME = "44201";
    private final Mail MAIL = new Mail(RESPONSIBLE_MAIL_ADDRESS, TO_ADDRESS, null, null);
    private List<Person> admins;
    private MailNotificationFactory mailFactory;
    @Mock
    private Person responsibleMock;
    @Mock
    private Person adminMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private PersonRepository personRepositoryMock;
    @Mock
    private Request requestMock;
    @Mock
    private Room roomMock;
    @Mock
    private RoomRepository roomRepositoryMock;

    @Before
    public void initializeMailFactory() {
        when(responsibleMock.getID()).thenReturn(RESPONSIBLE_UUID);
        when(responsibleMock.getMailAddress()).thenReturn(RESPONSIBLE_MAIL_ADDRESS);
        when(responsibleMock.isAdmin()).thenReturn(false);
        when(adminMock.getID()).thenReturn(ADMIN_UUID);
        when(adminMock.getMailAddress()).thenReturn(ADMIN_MAIL_ADDRESS);
        when(adminMock.isAdmin()).thenReturn(true);
        admins = Arrays.asList(adminMock);
        when(requestMock.getRequestID()).thenReturn(REQUEST_UUID);
        mailFactory = new MailNotificationFactory(mailSenderMock, roomRepositoryMock, personRepositoryMock);
    }

    @Test
    public void givenMailFactory_WhenRequestSuccess_ThenShouldHaveCorrectMailNotificationCreated() throws RoomNotFoundException, PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(roomRepositoryMock.findRoomByAssociatedRequest(requestMock)).thenReturn(roomMock);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(responsibleMock);
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertEquals(returnedNotification.mailToSend.to, MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenAcceptedRequestNotAssociatedWithRoom_WhenCreate_ThenThrowInvalidRequest() throws RoomNotFoundException, PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        doThrow(RoomNotFoundException.class).when(roomRepositoryMock).findRoomByAssociatedRequest(requestMock);

        mailFactory.createNotification(requestMock);
    }

    @Test
    public void givenRefusedRequest_WhenCreate_ThenShouldCreateMailNotification() throws PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(responsibleMock);
        when(personRepositoryMock.findAdmins()).thenReturn(admins);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_REFUSED));
    }

    @Test
    public void givenCanceledRequest_WhenCreate_ThenShouldCreateMailNotification() throws PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.CANCELED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(responsibleMock);
        when(personRepositoryMock.findAdmins()).thenReturn(admins);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_CANCELED));
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithoutResponsible_WhenCreate_ThenShouldThrowNoResponsible() throws PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        doThrow(PersonNotFoundException.class).when(personRepositoryMock).findByUUID(RESPONSIBLE_UUID);

        mailFactory.createNotification(requestMock);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithNoRequestStatus_WhenCreate_ThenShouldThrowNoRequestStatus() throws PersonNotFoundException {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(null);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(responsibleMock);
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        mailFactory.createNotification(requestMock);
    }
}
