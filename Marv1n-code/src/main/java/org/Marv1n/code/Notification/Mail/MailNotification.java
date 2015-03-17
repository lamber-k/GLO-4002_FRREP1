package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notification.Notification;
import org.Marv1n.code.Notification.Mail.MailService.MailService;

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
