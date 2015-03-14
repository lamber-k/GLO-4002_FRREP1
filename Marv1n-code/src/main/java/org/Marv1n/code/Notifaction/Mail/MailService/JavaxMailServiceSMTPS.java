package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;


public class JavaxMailServiceSMTPS extends JavaxMailService {

    public JavaxMailServiceSMTPS(MailServiceOptions options, IMailTransporter mailTransporter) {
        this.mailTransporter = mailTransporter;
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
    protected Properties additionalProperties(Properties properties) {
        properties.put("mail.smtp.socketFactory.port", options.port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
