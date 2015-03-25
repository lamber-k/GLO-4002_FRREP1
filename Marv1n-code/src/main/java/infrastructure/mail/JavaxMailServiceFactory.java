package infrastructure.mail;

import org.Marv1n.core.notification.mail.MailSender;

import javax.mail.Authenticator;

public class JavaxMailServiceFactory {

    public MailSender createMailService(Protocol protocol, MailServiceOptions options, MailTransporter mailTransporter) {
        switch (protocol) {
            case SMTPS:
                PasswordBasedAuthenticator authenticatorSMTPS = new PasswordBasedAuthenticator(options);
                return new JavaxMailSenderSMTPS(options, mailTransporter, authenticatorSMTPS);
            case SSL:
                PasswordBasedAuthenticator authenticatorSSL = new PasswordBasedAuthenticator(options);
                return new JavaxMailSenderSSL(options, mailTransporter, authenticatorSSL);
            case SMTP:
            default:
                return new JavaxMailSenderSMTP(options, mailTransporter, new Authenticator() {
                });
        }
    }
}
