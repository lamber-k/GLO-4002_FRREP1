package MailSender;

import org.Marv1n.core.Notification.Mail.Mail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailSenderSMTPTest {

    private static final String DESTINATION_MAIL = "to@exemple.com";
    private static final String FROM_MAIL = "from@exemple.com";
    private JavaxMailSenderSMTP mailServiceSMTP;
    private Mail mail;
    @Mock
    private MailTransporter mailTransporterMock;
    @Mock
    private Authenticator authenticatorMock;

    @Before
    public void initializeMailServiceSMTP() {
        MailServiceOptions mailServiceOptions = new MailServiceOptions("Host", "Port");
        mail = initializeMail();
        mailServiceSMTP = new JavaxMailSenderSMTP(mailServiceOptions, mailTransporterMock, authenticatorMock);
    }

    public Mail initializeMail() {
        List<String> destination = new ArrayList<>();
        destination.add(DESTINATION_MAIL);
        return new Mail(FROM_MAIL, destination, "Subject", "Message");
    }

    @Test
    public void givenMailServiceSFTP_WhenSendCalledAnSendingSuccess_ThenMailTransporterSendShouldBeCalled() throws MessagingException {
        mailServiceSMTP.send(mail);

        verify(mailTransporterMock).send(any(Message.class));
    }

    @Test(expected = RuntimeException.class)
    public void givenMailServiceSMTP_WhenSendCalledAnSendingFailWithException_ThenExceptionShouldBeThrow() throws MessagingException {
        doThrow(new MessagingException()).when(mailTransporterMock).send(any(Message.class));

        mailServiceSMTP.send(mail);
    }
}
