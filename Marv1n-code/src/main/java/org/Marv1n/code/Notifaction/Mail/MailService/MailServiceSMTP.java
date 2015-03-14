package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class MailServiceSMTP extends JavaxMailService {

    public MailServiceSMTP(MailServiceOptions options, IMailTransporter mailTransporter) {
        this.mailTransporter = mailTransporter;
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, new Authenticator() {
        });
    }

    @Override
    protected Properties additionalProperties(Properties properties) {
        return properties;
    }
}
