package org.Marv1n.core.Notification.Mail;

import org.Marv1n.core.Notification.Mail.MailService.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailNotificationTest {

    private static final String MAIL_ADDRESS = "test@test.ca";
    private static final String OBJECT = "test";
    private static final String MESSAGE = "Hello, World.";
    private static final List<String> TO_ADDRESS = Arrays.asList(MAIL_ADDRESS);
    private final Mail MAIL = new Mail(MAIL_ADDRESS, TO_ADDRESS, OBJECT, MESSAGE);
    private MailNotification notifier;
    @Mock
    private MailService mailServiceMock;

    @Before
    public void initializeMailNotifier() {
        notifier = new MailNotification(mailServiceMock, MAIL);
    }

    @Test
    public void givenNotifier_WhenSendMail_ThenShouldStartSend() {
        notifier.announce();
        verify(mailServiceMock).send(MAIL);
    }
}
