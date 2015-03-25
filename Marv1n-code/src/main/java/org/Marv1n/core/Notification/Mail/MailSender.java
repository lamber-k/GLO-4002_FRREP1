package org.Marv1n.core.Notification.Mail;

@FunctionalInterface
public interface MailSender {

    void send(Mail mail);
}
