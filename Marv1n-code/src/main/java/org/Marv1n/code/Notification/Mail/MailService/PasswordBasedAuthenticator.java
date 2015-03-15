package org.Marv1n.code.Notification.Mail.MailService;

import javax.mail.Authenticator;

public class PasswordBasedAuthenticator extends Authenticator {
    private MailServiceOptions options;

    public PasswordBasedAuthenticator(MailServiceOptions options) {
        this.options = options;
    }

    @Override
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(options.username, options.password);
    }
}
