package core.notification.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationTest {

    private MailNotification notifier;
    @Mock
    private Mail mailMock;
    @Mock
    private MailSender mailSenderMock;

    @Before
    public void initializeMailNotifier() {
        notifier = new MailNotification(mailSenderMock, mailMock);
    }

    @Test
    public void givenNotifier_WhenSendMail_ThenShouldStartSend() {
        notifier.announce();
        verify(mailSenderMock).send(mailMock);
    }
}
