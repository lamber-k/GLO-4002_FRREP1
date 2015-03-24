package org.Marv1n.core.Notification.Mail;

import org.Marv1n.core.Notification.Mail.MailService.MailService;
import org.Marv1n.core.Notification.Notification;

public class MailNotification implements Notification {

    public final Mail mailToSend;
    private MailService service;

    public MailNotification(MailService service, Mail toSend) {
        this.service = service;
        this.mailToSend = toSend;
    }

    @Override
    public void announce() {
        service.send(mailToSend);
    }
}
