package MailSender;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MailTransporter {

    void send(Message message) throws MessagingException;
}
