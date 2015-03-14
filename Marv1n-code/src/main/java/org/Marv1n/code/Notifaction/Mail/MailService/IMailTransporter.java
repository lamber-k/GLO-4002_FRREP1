package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface IMailTransporter {

    public void send(Message message) throws MessagingException;
}
