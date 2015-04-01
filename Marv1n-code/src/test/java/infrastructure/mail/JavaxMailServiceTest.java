package infrastructure.mail;

import core.notification.mail.Mail;
import core.notification.mail.MailAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailServiceTest {

    private static final String FROM_MAIL = "from@exemple.com";
    private static final String TO_MAIL = "to@exemple.com";
    private JavaxMailSender mailServiceSSL;
    private Mail mail;
    @Mock
    private MailAddress fromMailMock;
    @Mock
    private MailAddress toMailMock;
    @Mock
    private JavaxMailTransporter mailTransporterMock;

    @Before
    public void initializeMailService() throws IOException {
        mail = initializeMail();
        mailServiceSSL = new JavaxMailSender(mailTransporterMock);
        when(fromMailMock.toString()).thenReturn(FROM_MAIL);
        when(toMailMock.toString()).thenReturn(TO_MAIL);
    }

    public Mail initializeMail() {
        List<MailAddress> destination = new ArrayList<>();
        destination.add(toMailMock);
        return new Mail(fromMailMock, destination, "Subject", "Message");
    }

    @Test
    public void WhenSendCalledAnSendingSuccess_ThenMailTransporterSendShouldBeCalled() throws MessagingException {
        mailServiceSSL.send(mail);

        verify(mailTransporterMock).send(any(Message.class));
    }

    @Test(expected = RuntimeException.class)
    public void WhenSendCalledAnSendingFailWithException_ThenExceptionShouldBeThrow() throws MessagingException {
        doThrow(new MessagingException()).when(mailTransporterMock).send(any(Message.class));

        mailServiceSSL.send(mail);
    }
}