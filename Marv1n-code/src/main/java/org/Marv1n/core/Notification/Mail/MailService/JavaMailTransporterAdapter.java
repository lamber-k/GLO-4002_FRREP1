package org.Marv1n.core.Notification.Mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class JavaMailTransporterAdapter implements MailTransporter {

    @Override
    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
}
