package org.Marv1n.code.Notifaction.Mail.MailService;

import javax.mail.*;
import java.util.Properties;

public class JavaxMailServiceSMTP extends JavaxMailService {

    public JavaxMailServiceSMTP(MailServiceOptions options) {
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
