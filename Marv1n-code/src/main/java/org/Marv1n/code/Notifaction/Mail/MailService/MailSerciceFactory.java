package org.Marv1n.code.Notifaction.Mail.MailService;

public class MailSerciceFactory {


    public IMailService createMailService(Protocol protocol,MailServiceOptions options){

        switch (protocol) {
            case SMTPS:
                return new JavaxMailServiceSMTPS(options);
            case SSL:
                return new JavaxMailServiceSSL(options);
            default:
                return new JavaxMailServiceSMTP(options);
        }
    }

}
