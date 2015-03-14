package org.Marv1n.code.Notifaction.Mail;

import org.Marv1n.code.Notifaction.INotification;
import org.Marv1n.code.Notifaction.Mail.MailService.IMailService;

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
