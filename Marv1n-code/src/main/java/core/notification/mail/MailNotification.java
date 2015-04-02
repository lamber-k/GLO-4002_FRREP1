package core.notification.mail;

import core.notification.Notification;

public class MailNotification implements Notification {

    private final Mail mailToSend;
    private MailSender service;

    public MailNotification(MailSender service, Mail toSend) {
        this.service = service;
        this.mailToSend = toSend;
    }

    @Override
    public void announce() {
        service.send(getMailToSend());
    }

    public Mail getMailToSend() {
        return mailToSend;
    }
}
