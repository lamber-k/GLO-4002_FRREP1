package core.notification.mail;

@FunctionalInterface
public interface MailSender {

    void send(Mail mail) throws MailSendingException;
}
