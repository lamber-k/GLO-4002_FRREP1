package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.MailService;
import org.junit.Test;

public class MailServiceTest {
    @Test
    public void canCreateMailService() {
        MailService.MailServiceOptions options = new MailService.MailServiceOptions("host", "ports");
        new MailService(options);
    }
}
