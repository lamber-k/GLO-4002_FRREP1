package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.InvalidRequestException;
import org.Marv1n.code.Notifaction.Mail.IMailServiceAdapter;
import org.Marv1n.code.Notifaction.Mail.Mail;
import org.Marv1n.code.Notifaction.Mail.MailFactoryNotification;
import org.Marv1n.code.Notifaction.Mail.MailNotification;
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
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    private IMailServiceAdapter mockMailService;
    private MailFactoryNotification mailFactory;
    private ReservationRepository mockReservationRepository;
    private ReservableRepository mockReservableRepository;
    private PersonRepository mockPersonRepository;
    private Request mockRequest;
    private Reservation mockReservation;
    private IReservable mockReservable;

    @Before
    public void initializeMailFactory() {
        mockMailService = mock(IMailServiceAdapter.class);
        mockReservableRepository = mock(ReservableRepository.class);
        mockReservationRepository = mock(ReservationRepository.class);
        mockPersonRepository = mock(PersonRepository.class);
        mockRequest = mock(Request.class);
        mockReservation = mock(Reservation.class);
        mockReservable = mock(IReservable.class);
        when(mockRequest.getRequestID()).thenReturn(A_REQUEST_UUID);
        mailFactory = new MailFactoryNotification(mockMailService);
    }

    @Test
    public void givenMailFactory_whenRequestSuccess_thenShouldHaveCorrectMailNotificationCreated() {
        when(mockRequest.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(mockPersonRepository.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(mockPersonRepository.findAdmins()).thenReturn(ADMINS);
        when(mockReservationRepository.findReservationByRequest(mockRequest)).thenReturn(mockReservation);
        when(mockReservation.getReserved()).thenReturn(mockReservable);
        when(mockReservable.getName()).thenReturn(A_RESERVABLE_NAME);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(mockRequest, mockReservationRepository, mockReservableRepository, mockPersonRepository);

        assertEquals(returnedNotification.mailToSend.to, A_MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_whenCreate_thenThrow() {
        when(mockRequest.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(mockPersonRepository.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        doThrow(ReservationNotFoundException.class).when(mockReservationRepository).findReservationByRequest(mockRequest);

        mailFactory.createNotification(mockRequest, mockReservationRepository, mockReservableRepository, mockPersonRepository);
    }

    @Test
    public void givenRefusedRequest_whenCreate_thenShouldCreateMailNotification() {
        when(mockRequest.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.REFUSED);
        when(mockPersonRepository.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(mockPersonRepository.findAdmins()).thenReturn(ADMINS);
        doThrow(ReservationNotFoundException.class).when(mockReservationRepository).findReservationByRequest(mockRequest);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(mockRequest, mockReservationRepository, mockReservableRepository, mockPersonRepository);

        assertTrue(returnedNotification.mailToSend.object.contains("refusée"));
    }

    @Test
    public void givenCanceledRequest_whenCreate_thenShouldCreateMailNotification() {
        when(mockRequest.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.CANCELED);
        when(mockPersonRepository.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.of(A_RESPONSIBLE));
        when(mockPersonRepository.findAdmins()).thenReturn(ADMINS);
        when(mockReservationRepository.findReservationByRequest(mockRequest)).thenReturn(mockReservation);
        when(mockReservation.getReserved()).thenReturn(mockReservable);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(mockRequest, mockReservationRepository, mockReservableRepository, mockPersonRepository);

        assertTrue(returnedNotification.mailToSend.object.contains("annulée"));
    }

    @Test(expected = InvalidRequestException.class)
    public void givenRequestWithoutResponsible_whenCreate_thenShouldThrowNoResponsible() {
        when(mockRequest.getResponsibleUUID()).thenReturn(A_RESPONSIBLE.getID());
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(mockPersonRepository.findByUUID(A_RESPONSIBLE.getID())).thenReturn(Optional.empty());

        mailFactory.createNotification(mockRequest, mockReservationRepository, mockReservableRepository, mockPersonRepository);
    }
}
