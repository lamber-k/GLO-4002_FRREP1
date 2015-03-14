package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class JavaMailTransporterAdapter implements IMailTransporter {

    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
}
