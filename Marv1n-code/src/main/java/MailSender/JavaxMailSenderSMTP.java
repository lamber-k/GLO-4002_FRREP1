package MailSender;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class JavaxMailSenderSMTP extends JavaxMailSender {

    public JavaxMailSenderSMTP(MailServiceOptions options, MailTransporter mailTransporter, Authenticator authenticator) {
        this.mailTransporter = mailTransporter;
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, authenticator);
    }

    @Override
    protected Properties additionalProperties(Properties properties) {
        return properties;
    }
}
