package infrastructure.MailSender;

import javax.mail.Message;
import javax.mail.MessagingException;

@FunctionalInterface
public interface MailTransporter {

    void send(Message message) throws MessagingException;
}
