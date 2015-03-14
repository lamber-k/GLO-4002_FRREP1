package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.Mail;
import org.Marv1n.code.Notifaction.Mail.MailNotification;
import org.Marv1n.code.Notifaction.Mail.MailService.IMailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationTest {

    private final String A_MAIL_ADDRESS = "test@test.ca";
    private final String AN_OBJECT = "test";
    private final String A_MESSAGE = "Hello, World.";
    private final List<String> TO_ADDRESS = Arrays.asList(A_MAIL_ADDRESS);
    private final Mail A_MAIL = new Mail(A_MAIL_ADDRESS, TO_ADDRESS, AN_OBJECT, A_MESSAGE);
    private MailNotification notifier;

    @Mock
    private IMailService mailServiceMock;

    @Before
    public void initializeNotifier() {
        mailServiceMock = mock(IMailService.class);
        notifier = new MailNotification(mailServiceMock, A_MAIL);
    }

    @Test
    public void givenNotifier_WhenSendMail_ThenShouldStartSend() {
        notifier.announce();
        verify(mailServiceMock).send(A_MAIL);
    }
}
