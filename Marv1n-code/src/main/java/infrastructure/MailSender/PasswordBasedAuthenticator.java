package infrastructure.MailSender;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PasswordBasedAuthenticator extends Authenticator {

    private MailServiceOptions options;

    public PasswordBasedAuthenticator(MailServiceOptions options) {
        this.options = options;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(options.username, options.password);
    }
}
