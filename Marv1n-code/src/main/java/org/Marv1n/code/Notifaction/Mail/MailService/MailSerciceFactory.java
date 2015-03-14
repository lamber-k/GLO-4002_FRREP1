package org.Marv1n.code.Notifaction.Mail.MailService;

public class MailSerciceFactory {


    public IMailService createMailService(Protocol protocol, MailServiceOptions options, IMailTransporter mailTransporter) {

        switch (protocol) {
            case SMTPS:
                return new MailServiceSMTPS(options, mailTransporter);
            case SSL:
                return new MailServiceSSL(options, mailTransporter);
            default:
                return new MailServiceSMTP(options, mailTransporter);
        }
    }

}
