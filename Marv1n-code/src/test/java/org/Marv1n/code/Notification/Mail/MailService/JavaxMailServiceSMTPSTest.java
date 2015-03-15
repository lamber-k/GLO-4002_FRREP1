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

    @Mock
    private IMailTransporter mailTransporterMock;

    @Mock
    private PasswordBasedAuthenticator passwordBasedAuthenticatorMock;

    private JavaxMailServiceSMTPS mailServiceSMTPS;
    private MailServiceOptions mailServiceOptions;
    private Mail mail;

    @Before
    public void init() {
        mailServiceOptions = new MailServiceOptions("Host", "Port", "Username", "Password");
        mail = initMail();
        mailServiceSMTPS = new JavaxMailServiceSMTPS(mailServiceOptions, mailTransporterMock, passwordBasedAuthenticatorMock);
    }

    public Mail initMail() {
        List<String> destination = new ArrayList<>();
        destination.add("to@exemple.com");
        return new Mail("from@exemple.com", destination, "Subject", "Message");
    }

    @Test
    public void givenMailServiceSMTPS_WhenSendCalledAnSendingSucces_ThenMailTransporterSendShouldBeCalled() throws MessagingException {
        mailServiceSMTPS.send(mail);

        verify(mailTransporterMock).send(any(Message.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenMailServiceSMTPS_WhenSendCalledAnSendingFailWithException_ThenExceptionShouldBeThrow() throws MessagingException {
        doThrow(new MessagingException()).when(mailTransporterMock).send(any(Message.class));

        mailServiceSMTPS.send(mail);
    }


}