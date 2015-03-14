package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.MailService.MailServiceOptions;
import org.Marv1n.code.Notifaction.Mail.MailService.MailServiceSMTP;
import org.junit.Test;

public class MailServiceSMTPTest {

    @Test
    public void canCreateMailService() {
        MailServiceOptions options = new MailServiceOptions("host", "ports");
        new MailServiceSMTP(options);
    }

}
