package ca.ulaval.glo4002.core.notification.mail;

@FunctionalInterface
public interface MailSender {

    void send(Mail mail);
}
