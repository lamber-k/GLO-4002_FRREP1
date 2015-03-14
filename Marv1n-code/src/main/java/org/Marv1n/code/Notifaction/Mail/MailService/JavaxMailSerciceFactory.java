package org.Marv1n.code.Notifaction.Mail.MailService;

public class JavaxMailSerciceFactory {


    public IMailService createMailService(Protocol protocol, MailServiceOptions options, IMailTransporter mailTransporter) {

        switch (protocol) {
            case SMTPS:
                return new JavaxMailServiceSMTPS(options, mailTransporter);
            case SSL:
                return new JavaxMailServiceSSL(options, mailTransporter);
            default:
                return new JavaxMailServiceSMTP(options, mailTransporter);
        }
    }

}
