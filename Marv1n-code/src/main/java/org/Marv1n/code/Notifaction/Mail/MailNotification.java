package org.Marv1n.code.Notifaction.Mail;

import org.Marv1n.code.Notifaction.INotification;


public class MailNotification implements INotification {

    public final Mail mailToSend;
    private IMailServiceAdapter service;

    public MailNotification(IMailServiceAdapter service, Mail toSend) {
        this.service = service;
        mailToSend = toSend;
    }

    @Override
    public void announce() {
        service.send(mailToSend);
    }
}
