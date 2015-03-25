package org.Marv1n.core.Notification.Mail;

import Persistence.PersonRepositoryInMemory;
import Persistence.ReservationRepositoryInMemory;
import org.Marv1n.core.Notification.InvalidRequestException;
import org.Marv1n.core.Person.Person;
import org.Marv1n.core.Request.Request;
import org.Marv1n.core.Request.RequestStatus;
import org.Marv1n.core.Reservation.Reservation;
import org.Marv1n.core.Reservation.ReservationNotFoundException;
import org.Marv1n.core.Room.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    private static final String RESPONSIBLE_MAIL_ADDRESS = "responsible@test.ca";
    private static final String ADMIN_MAIL_ADDRESS = "admin@test.ca";
    private static final List<String> TO_ADDRESS = Arrays.asList(RESPONSIBLE_MAIL_ADDRESS, ADMIN_MAIL_ADDRESS);
    private static final UUID REQUEST_UUID = UUID.randomUUID();
    private static final String RESERVABLE_NAME = "44201";
    private final Mail MAIL = new Mail(RESPONSIBLE_MAIL_ADDRESS, TO_ADDRESS, null, null);
    private final Person RESPONSIBLE = new Person(RESPONSIBLE_MAIL_ADDRESS);
    private final Person ADMIN = new Person(ADMIN_MAIL_ADDRESS);
    private final List<Person> ADMINS = Arrays.asList(ADMIN);
    private MailNotificationFactory mailFactory;
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
        when(requestMock.getRequestID()).thenReturn(REQUEST_UUID);
        mailFactory = new MailNotificationFactory(mailSenderMock, reservationRepositoryMock, personRepositoryMock);
    }

    @Test
    public void givenMailFactory_WhenRequestSuccess_ThenShouldHaveCorrectMailNotificationCreated() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertEquals(returnedNotification.mailToSend.to, MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_WhenCreate_ThenThrow() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        mailFactory.createNotification(requestMock);
    }

    @Test
    public void givenRefusedRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_REFUSED));
    }

    @Test
    public void givenCanceledRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.CANCELED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);

        MailNotification returnedNotification = mailFactory.createNotification(requestMock);

        assertTrue(returnedNotification.mailToSend.object.contains(MailBuilder.MAIL_OBJECT_STATUS_CANCELED));
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithoutResponsible_WhenCreate_ThenShouldThrowNoResponsible() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.empty());

        mailFactory.createNotification(requestMock);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithNoRequestStatus_WhenCreate_ThenShouldThrowNoRequestStatus() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(null);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(roomMock);
        when(roomMock.getName()).thenReturn(RESERVABLE_NAME);

        mailFactory.createNotification(requestMock);
    }
}
