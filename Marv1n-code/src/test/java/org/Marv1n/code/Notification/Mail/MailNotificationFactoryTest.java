package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notification.InvalidRequestException;
import org.Marv1n.code.Notification.Mail.MailService.MailService;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepositoryInMemory;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepositoryInMemory;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservable.Reservable;
import org.Marv1n.code.Reservation.Reservation;
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
    private static final Mail MAIL = new Mail(RESPONSIBLE_MAIL_ADDRESS, TO_ADDRESS, null, null);
    private static final Person RESPONSIBLE = new Person(RESPONSIBLE_MAIL_ADDRESS);
    private static final Person ADMIN = new Person(ADMIN_MAIL_ADDRESS);
    private static final UUID REQUEST_UUID = UUID.randomUUID();
    private static final String RESERVABLE_NAME = "44201";
    private static final List<Person> ADMINS = Arrays.asList(ADMIN);
    private MailNotificationAbstractFactory mailFactory;
    @Mock
    private MailService mailServiceMock;
    @Mock
    private ReservationRepositoryInMemory reservationRepositoryMock;
    @Mock
    private PersonRepositoryInMemory personRepositoryMock;
    @Mock
    private Request requestMock;
    @Mock
    private Reservation reservationMock;
    @Mock
    private Reservable reservableMock;

    @Before
    public void initializeMailFactory() {
        when(requestMock.getRequestID()).thenReturn(REQUEST_UUID);
        mailFactory = new MailNotificationAbstractFactory(mailServiceMock, reservationRepositoryMock, personRepositoryMock);
    }

    @Test
    public void givenMailFactory_WhenRequestSuccess_ThenShouldHaveCorrectMailNotificationCreated() {
        when(requestMock.getResponsibleUUID()).thenReturn(RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(RESPONSIBLE.getID())).thenReturn(Optional.of(RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(reservableMock);
        when(reservableMock.getName()).thenReturn(RESERVABLE_NAME);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(requestMock);

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
        when(reservationMock.getReserved()).thenReturn(reservableMock);

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
        when(reservationMock.getReserved()).thenReturn(reservableMock);
        when(reservableMock.getName()).thenReturn(RESERVABLE_NAME);

        mailFactory.createNotification(requestMock);
    }
}
