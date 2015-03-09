package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.IFactoryNotification;
import org.Marv1n.code.Notifaction.INotification;
import org.Marv1n.code.Notifaction.Mail.IMailServiceAdapter;
import org.Marv1n.code.Notifaction.Mail.MailFactoryNotification;
import org.Marv1n.code.Notification.InvalidRequestException;
import org.Marv1n.code.Repository.Request.RequestRepository;
import org.Marv1n.code.Repository.Reservable.ReservableRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailFactoryNotificationTest {

    private static final UUID   A_REQUEST_UUID = UUID.randomUUID();

    private IMailServiceAdapter mockMailService;
    private MailFactoryNotification mailFactory;
    private RequestRepository mockRequestRepository;
    private ReservableRepository mockReservableRepository;

    @Before
    public void initializeMailFactory() {
        mockMailService = mock(IMailServiceAdapter.class);
        mailFactory = new MailFactoryNotification(mockMailService);
    }

    @Test
    public void givenMailFactory_whenNotifySuccess_thenShouldCreateNotificationSuccess() {
        INotification returnedNotification = mailFactory.createNotification(A_REQUEST_UUID, mockRequestRepository, mockReservableRepository, IFactoryNotification.StateNotification.ASSIGNATION_SUCCESS);

        assertNotNull(returnedNotification);
    }

    @Test(expected = InvalidRequestException.class)
    public void givenWrongRequestUUID_whenCreate_thenThrow() {
        when(mockRequestRepository.findByUUID(A_REQUEST_UUID)).thenReturn(Optional.empty());
        mailFactory.createNotification(A_REQUEST_UUID, mockRequestRepository, mockReservableRepository, IFactoryNotification.StateNotification.ASSIGNATION_SUCCESS);
    }
}
