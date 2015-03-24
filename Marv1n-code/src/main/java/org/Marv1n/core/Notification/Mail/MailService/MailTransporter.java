package org.Marv1n.core.Notification.Mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MailTransporter {

    void send(Message message) throws MessagingException;
}
