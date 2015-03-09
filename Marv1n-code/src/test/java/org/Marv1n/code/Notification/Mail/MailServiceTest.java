package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.MailService;
import org.junit.Test;

/**
 * Created by Rafaël on 09/03/2015.
 */
public class MailServiceTest {
    @Test
    public void canCreateMailService() {
        MailService.MailServiceOptions options = new MailService.MailServiceOptions("host", "ports");
        new MailService(options);
    }
}
