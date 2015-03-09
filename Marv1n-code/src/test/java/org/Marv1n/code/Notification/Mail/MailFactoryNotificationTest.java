package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.IFactoryNotification;
import org.Marv1n.code.Notifaction.Mail.IMailServiceAdapter;
import org.Marv1n.code.Notifaction.Mail.Mail;
import org.Marv1n.code.Notifaction.Mail.MailFactoryNotification;
import org.Marv1n.code.Notifaction.InvalidRequestException;
import org.Marv1n.code.Notifaction.Mail.MailNotification;
import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.Person.PersonRepository;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.Marv1n.code.Request;
import org.Marv1n.code.Reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailFactoryNotificationTest {

    private static final String A_MAIL_ADDRESS = "test@test.ca";
    private static final String ANOTHER_MAIL_ADDRESS = "test2@test.ca";
    private static final String AN_OBJECT = "test";
    private static final String A_MESSAGE = "Hello, World.";
    private static final List<String> TO_ADDRESS = Arrays.asList(A_MAIL_ADDRESS, ANOTHER_MAIL_ADDRESS);
    private static final Mail A_MAIL = new Mail(A_MAIL_ADDRESS, TO_ADDRESS, AN_OBJECT, A_MESSAGE);
    private static final Person A_RESPONSIBLE = new Person(A_MAIL_ADDRESS);
    private static final Person AN_ADMIN = new Person(ANOTHER_MAIL_ADDRESS);
    private static final List<Person> PERSON_TO_NOTIFY = Arrays.asList(A_RESPONSIBLE, AN_ADMIN);
    private static final List<UUID> UUIDS_TO_NOTIFY = Arrays.asList(A_RESPONSIBLE.getID(), AN_ADMIN.getID());
    private static final UUID A_REQUEST_UUID = UUID.randomUUID();

    private IMailServiceAdapter mockMailService;
    private MailFactoryNotification mailFactory;
    private RequestRepository mockRequestRepository;
    private ReservableRepository mockReservableRepository;
    private PersonRepository mockPersonRepository;
    private Request mockRequest;

    @Before
    public void initializeMailFactory() {
        mockMailService = mock(IMailServiceAdapter.class);
        mockRequestRepository = mock(RequestRepository.class);
        mockReservableRepository = mock(ReservableRepository.class);
        mockPersonRepository = mock(PersonRepository.class);
        mockRequest = mock(Request.class);
        when(mockRequest.getRequestID()).thenReturn(A_REQUEST_UUID);
        mailFactory = new MailFactoryNotification(mockMailService);
    }

    @Test
    public void givenMailFactory_whenNotifySuccess_thenShouldHaveAdminAndResponsibleMails() {
        when(mockRequestRepository.findByUUID(A_REQUEST_UUID)).thenReturn(Optional.of(mockRequest));
        //when(mockRequest.getResponsible()).thenReturn(A_RESPONSIBLE);
        //when(mockPersonRepository.getAdmin()).thenReturn(AN_ADMIN);
        when(mockPersonRepository.findByListOfUUID(UUIDS_TO_NOTIFY)).thenReturn(PERSON_TO_NOTIFY);

        MailNotification returnedNotification = (MailNotification) mailFactory.createNotification(A_REQUEST_UUID, mockRequestRepository, mockReservableRepository, IFactoryNotification.StateNotification.ASSIGNATION_SUCCESS, mockPersonRepository);

        assertEquals(returnedNotification.mailToSend.to, A_MAIL.to);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_whenCreate_thenThrow() {
        when(mockRequestRepository.findByUUID(A_REQUEST_UUID)).thenReturn(Optional.empty());
        mailFactory.createNotification(A_REQUEST_UUID, mockRequestRepository, mockReservableRepository, IFactoryNotification.StateNotification.ASSIGNATION_SUCCESS, mockPersonRepository);
    }
}
