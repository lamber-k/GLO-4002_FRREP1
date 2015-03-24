package org.Marv1n.core.Notification.Mail.MailService;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class JavaxMailServiceSSL extends JavaxMailService {

    public JavaxMailServiceSSL(MailServiceOptions options, MailTransporter mailTransporter, Authenticator authenticator) {
        this.mailTransporter = mailTransporter;
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, authenticator);
    }

    @Override
    protected Properties additionalProperties(Properties properties) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }
}

