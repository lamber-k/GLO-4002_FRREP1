package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notification.INotification;
import org.Marv1n.code.Notification.Mail.MailService.IMailService;

public class MailNotification implements INotification {

    public final Mail mailToSend;
    private IMailService service;

    public MailNotification(IMailService service, Mail toSend) {
        this.service = service;
        mailToSend = toSend;
    }

    @Override
    public void announce() {
        service.send(mailToSend);
    }
}
