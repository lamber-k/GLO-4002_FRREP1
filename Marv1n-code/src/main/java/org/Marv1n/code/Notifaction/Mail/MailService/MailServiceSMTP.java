package org.Marv1n.code.Notifaction.Mail.MailService;

import org.Marv1n.code.Notifaction.Mail.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailServiceSMTP extends JavaxMailService {

    public MailServiceSMTP(MailServiceOptions options) {
        this.options = options;
        Properties properties = this.setupProperties();
        this.session = Session.getInstance(properties, new Authenticator() {
        });
    }

    @Override
    protected Properties  additionalProperties(Properties properties) {
        return properties;
    }
}
