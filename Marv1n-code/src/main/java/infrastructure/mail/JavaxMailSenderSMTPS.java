package infrastructure.mail;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class JavaxMailSenderSMTPS extends JavaxMailSender {

    public JavaxMailSenderSMTPS(MailServiceOptions options, MailTransporter mailTransporter, Authenticator authenticator) {
        this.mailTransporter = mailTransporter;
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, authenticator);
    }

    @Override
    protected Properties additionalProperties(Properties properties) {
        properties.put("mail.smtp.socketFactory.port", options.port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
