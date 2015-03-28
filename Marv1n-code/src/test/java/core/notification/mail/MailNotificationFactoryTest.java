package core.notification.mail;

import core.notification.InvalidRequestException;
import core.person.Person;
import core.request.Request;
import core.request.RequestStatus;
import core.reservation.Reservation;
import core.reservation.ReservationNotFoundException;
import core.room.Room;
import infrastructure.persistence.PersonRepositoryInMemory;
import infrastructure.persistence.ReservationRepositoryInMemory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private ReservationRepositoryInMemory reservationRepositoryMock;
    @Mock
    private PersonRepositoryInMemory personRepositoryMock;
    @Mock
    private Request requestMock;
    @Mock
    private Reservation reservationMock;
    @Mock
    private Room roomMock;

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
        mailFactory = new MailNotificationFactory(mailSenderMock, reservationRepositoryMock, personRepositoryMock);
    }

    @Test
    public void givenMailFactory_WhenRequestSuccess_ThenShouldHaveCorrectMailNotificationCreated() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.of(responsibleMock));
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertEquals(returnedNotification.mailToSend.to, MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_WhenCreate_ThenThrow() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.of(responsibleMock));
        Mockito.doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        mailFactory.createNotification(requestMock);
    }

    @Test
    public void givenRefusedRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.of(responsibleMock));
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_REFUSED));
    }

    @Test
    public void givenCanceledRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.CANCELED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.of(responsibleMock));
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_CANCELED));
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithoutResponsible_WhenCreate_ThenShouldThrowNoResponsible() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.empty());

        mailFactory.createNotification(requestMock);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithNoRequestStatus_WhenCreate_ThenShouldThrowNoRequestStatus() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE_UUID);
        when(requestMock.getRequestStatus()).thenReturn(null);
        when(personRepositoryMock.findByUUID(RESPONSIBLE_UUID)).thenReturn(Optional.of(responsibleMock));
        when(personRepositoryMock.findAdmins()).thenReturn(admins);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        mailFactory.createNotification(requestMock);
    }
}
