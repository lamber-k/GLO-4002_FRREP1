package org.Marv1n.code.Notification.Mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MailTransporter {

    void send(Message message) throws MessagingException;
}
