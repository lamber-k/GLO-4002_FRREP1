package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.MailService.MailServiceOptions;
import org.Marv1n.code.Notifaction.Mail.MailService.JavaxMailServiceSMTP;
import org.junit.Test;

public class JavaxMailServiceSMTPTest {

    @Test
    public void canCreateMailService() {
        MailServiceOptions options = new MailServiceOptions("host", "ports");
        new JavaxMailServiceSMTP(options);
    }
}
