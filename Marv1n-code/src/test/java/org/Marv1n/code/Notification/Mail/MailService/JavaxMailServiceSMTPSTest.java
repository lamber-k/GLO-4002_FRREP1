package org.Marv1n.code.Notification.Mail.MailService;

import org.Marv1n.code.Notification.Mail.Mail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailServiceSMTPSTest {

    private static final String DESTINATION_MAIL = "to@exemple.com";
    private static final String FROM_MAIL = "from@exemple.com";
    private JavaxMailServiceSMTPS mailServiceSMTPS;
    private Mail mail;
    @Mock
    private MailTransporter mailTransporterMock;
    @Mock
    private PasswordBasedAuthenticator passwordBasedAuthenticatorMock;

    @Before
    public void initializeMailServiceSMTPS() {
        MailServiceOptions mailServiceOptions = new MailServiceOptions("Host", "Port", "Username", "Password");
        mail = initializeMail();
        mailServiceSMTPS = new JavaxMailServiceSMTPS(mailServiceOptions, mailTransporterMock, passwordBasedAuthenticatorMock);
    }

    public Mail initializeMail() {
        List<String> destination = new ArrayList<>();
        destination.add(DESTINATION_MAIL);
        return new Mail(FROM_MAIL, destination, "Subject", "Message");
    }

    @Test
    public void givenMailServiceSMTPS_WhenSendCalledAnSendingSuccess_ThenMailTransporterSendShouldBeCalled() throws MessagingException {
        mailServiceSMTPS.send(mail);

        verify(mailTransporterMock).send(any(Message.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenMailServiceSMTPS_WhenSendCalledAnSendingFailWithException_ThenExceptionShouldBeThrow() throws MessagingException {
        doThrow(new MessagingException()).when(mailTransporterMock).send(any(Message.class));

        mailServiceSMTPS.send(mail);
    }
}