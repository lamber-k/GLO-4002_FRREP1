package org.Marv1n.core.Notification.Mail.MailService;

import javax.mail.Authenticator;

public class JavaxMailServiceFactory {

    public MailService createMailService(Protocol protocol, MailServiceOptions options, MailTransporter mailTransporter) {
        switch (protocol) {
            case SMTPS:
                PasswordBasedAuthenticator authenticatorSMTPS = new PasswordBasedAuthenticator(options);
                return new JavaxMailServiceSMTPS(options, mailTransporter, authenticatorSMTPS);
            case SSL:
                PasswordBasedAuthenticator authenticatorSSL = new PasswordBasedAuthenticator(options);
                return new JavaxMailServiceSSL(options, mailTransporter, authenticatorSSL);
            case SMTP:
            default:
                return new JavaxMailServiceSMTP(options, mailTransporter, new Authenticator() {
                });
        }
    }
}
