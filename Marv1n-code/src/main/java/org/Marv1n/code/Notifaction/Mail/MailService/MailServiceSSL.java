package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.*;
import java.util.Properties;

public class MailServiceSSL extends JavaxMailService {

    public MailServiceSSL(MailServiceOptions options) {
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(options.username, options.password);
            }
        });
    }

    @Override
    protected Properties  additionalProperties(Properties properties) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }
}
