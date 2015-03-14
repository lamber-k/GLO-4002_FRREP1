package org.Marv1n.code.Notifaction.Mail.MailService;

import org.Marv1n.code.Notifaction.Mail.Mail;
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
public class JavaxMailServiceSMTPTest {

    @Mock
    private IMailTransporter mailTransporterMock;
    private JavaxMailServiceSMTP mailServiceSMTP;
    private MailServiceOptions mailServiceOptions;
    private Mail mail;

    @Before
    public void init() {
        mailServiceOptions = new MailServiceOptions("Host", "Port");
        mail = initMail();
        mailServiceSMTP = new JavaxMailServiceSMTP(mailServiceOptions, mailTransporterMock);
    }

    public Mail initMail() {
        List<String> destination = new ArrayList<>();
        destination.add("to@exemple.com");
        return new Mail("from@exemple.com", destination, "Subject", "Message");
    }

    @Test
    public void givenMailServiceSMTP_WhenSendCalledAnSendingSucces_ThenMailTransporterSendShouldBeCalled() throws MessagingException {
        mailServiceSMTP.send(mail);

        verify(mailTransporterMock).send(any(Message.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenMailServiceSMTP_WhenSendCalledAnSendingFailWithException_ThenExceptionShouldBeThrow() throws MessagingException {
        doThrow(new MessagingException()).when(mailTransporterMock).send(any(Message.class));

        mailServiceSMTP.send(mail);
    }


}
