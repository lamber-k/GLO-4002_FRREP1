package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.InvalidRequestException;
import org.Marv1n.code.Notifaction.Mail.Mail;
import org.Marv1n.code.Notifaction.Mail.MailFactoryNotification;
import org.Marv1n.code.Notifaction.Mail.MailNotification;
import org.Marv1n.code.Notifaction.Mail.MailService.IMailService;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Repository.Reservation.ReservationNotFoundException;
import org.Marv1n.code.Repository.Reservation.ReservationRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
import org.Marv1n.code.Reservable.IReservable;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailFactoryNotificationTest {

    private static final String RESPONSIBLE_MAIL_ADDRESS = "responsible@test.ca";
    private static final String ADMIN_MAIL_ADDRESS = "admin@test.ca";
    private static final List<String> TO_ADDRESS = Arrays.asList(RESPONSIBLE_MAIL_ADDRESS, ADMIN_MAIL_ADDRESS);
    private static final Mail A_MAIL = new Mail(RESPONSIBLE_MAIL_ADDRESS, TO_ADDRESS, null, null);
    private static final Person A_RESPONSIBLE = new Person(RESPONSIBLE_MAIL_ADDRESS);
    private static final Person AN_ADMIN = new Person(ADMIN_MAIL_ADDRESS);
    private static final UUID A_REQUEST_UUID = UUID.randomUUID();
    private static final String A_RESERVABLE_NAME = "44201";
    private static final List<Person> ADMINS = Arrays.asList(AN_ADMIN);

    private MailFactoryNotification mailFactory;
    @Mock
    private IMailService mailServiceMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private ReservableRepository reservableRepositoryMock;
    @Mock
    private PersonRepository personRepositoryMock;
    @Mock
    private Request requestMock;
    @Mock
    private Reservation reservationMock;
    @Mock
    private IReservable reservableMock;

    @Before
    public void initializeMailFactory() {
        mailServiceMock = mock(IMailService.class);
        reservableRepositoryMock = mock(ReservableRepository.class);
        reservationRepositoryMock = mock(ReservationRepository.class);
        personRepositoryMock = mock(PersonRepository.class);
        requestMock = mock(Request.class);
        reservationMock = mock(Reservation.class);
        reservableMock = mock(IReservable.class);
        when(requestMock.getRequestID()).thenReturn(A_REQUEST_UUID);
        mailFactory = new MailFactoryNotification(mailServiceMock);
    }

    @Test
    public void givenMailFactory_WhenRequestSuccess_ThenShouldHaveCorrectMailNotificationCreated() {
        when(requestMock.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(reservableMock);
        when(reservableMock.getName()).thenReturn(A_RESERVABLE_NAME);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(requestMock, reservationRepositoryMock, reservableRepositoryMock, personRepositoryMock);

        assertEquals(returnedNotification.mailToSend.to, A_MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_WhenCreate_ThenThrow() {
        when(requestMock.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        mailFactory.createNotification(requestMock, reservationRepositoryMock, reservableRepositoryMock, personRepositoryMock);
    }

    @Test
    public void givenRefusedRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(personRepositoryMock.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        doThrow(ReservationNotFoundException.class).when(reservationRepositoryMock).findReservationByRequest(requestMock);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(requestMock, reservationRepositoryMock, reservableRepositoryMock, personRepositoryMock);

        assertTrue(returnedNotification.mailToSend.object.contains("refusée"));
    }

    @Test
    public void givenCanceledRequest_WhenCreate_ThenShouldCreateMailNotification() {
        when(requestMock.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.CANCELED);
        when(personRepositoryMock.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(personRepositoryMock.findAdmins()).thenReturn(ADMINS);
        when(reservationRepositoryMock.findReservationByRequest(requestMock)).thenReturn(reservationMock);
        when(reservationMock.getReserved()).thenReturn(reservableMock);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(requestMock, reservationRepositoryMock, reservableRepositoryMock, personRepositoryMock);

        assertTrue(returnedNotification.mailToSend.object.contains("annulée"));
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithoutResponsible_WhenCreate_ThenShouldThrowNoResponsible() {
        when(requestMock.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(personRepositoryMock.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.empty());

        mailFactory.createNotification(requestMock, reservationRepositoryMock, reservableRepositoryMock, personRepositoryMock);
    }
}
