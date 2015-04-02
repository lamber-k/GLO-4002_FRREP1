package ca.ulaval.glo4002.Marv1n.applicationServices.mail;

import ca.ulaval.glo4002.core.notification.mail.Mail;
import ca.ulaval.glo4002.mail.JavaxMailSender;
import ca.ulaval.glo4002.mail.JavaxMailTransporter;
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

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailServiceTest {

    private static final String FROM_MAIL = "from@exemple.com";
    private static final String TO_MAIL = "to@exemple.com";
    private JavaxMailSender mailServiceSSL;
    private Mail mail;
    @Mock
    private JavaxMailTransporter mailTransporterMock;

    @Before
    public void initializeMailService() throws IOException {
        mail = initializeMail();
        mailServiceSSL = new JavaxMailSender(mailTransporterMock);
    }

    public Mail initializeMail() {
        List<String> destination = new ArrayList<>();
        destination.add(TO_MAIL);
        return new Mail(FROM_MAIL, destination, "Subject", "Message");
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