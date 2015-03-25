package org.Marv1n.core.notification.mail;

@FunctionalInterface
public interface MailSender {

    void send(Mail mail);
}
