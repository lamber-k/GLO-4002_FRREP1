package org.Marv1n.code.Notifaction.Mail.MailService;

public class MailSerciceFactory {


    public IMailService createMailService(Protocol protocol,MailServiceOptions options){

        switch (protocol) {
            case SMTPS:
                return new MailServiceSMTPS(options);
            case SSL:
                return new MailServiceSSL(options);
            default:
                return new MailServiceSMTP(options);
        }
    }

}
