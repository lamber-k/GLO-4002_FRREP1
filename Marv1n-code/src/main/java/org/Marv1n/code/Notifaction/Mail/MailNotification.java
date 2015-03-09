package org.Marv1n.code.Notifaction.Mail;

import org.Marv1n.code.Notifaction.INotification;

/**
 * Created by Kevin on 08/03/2015.
 */
public class MailNotification implements INotification {

    private final Mail mailToSend;
    private IMailServiceAdapter service;

    public MailNotification(IMailServiceAdapter service, Mail toSend) {
        this.service = service;
        this.mailToSend = toSend;
    }

    @Override
    public void announce() {
        service.send(mailToSend);
    }
}
