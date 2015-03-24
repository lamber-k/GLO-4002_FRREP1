package org.Marv1n.code.Notification.Mail.MailService;

import org.Marv1n.code.Notification.Mail.Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class JavaxMailService implements MailService {

    protected MailServiceOptions options;
    protected Session session;
    protected MailTransporter mailTransporter;

    protected Properties setupProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", options.host);
        properties.put("mail.smtp.port", options.port);
        additionalProperties(properties);
        return properties;
    }

    protected abstract Properties additionalProperties(Properties properties);

    @Override
    public void send(Mail mail) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail.from));

            List<InternetAddress> tos = new ArrayList<>();
            for (String to : mail.to) {
                tos.add(new InternetAddress(to));
            }
            InternetAddress[] toArray = tos.toArray(new InternetAddress[tos.size()]);
            message.addRecipients(Message.RecipientType.TO, toArray);
            message.setSubject(mail.object);
            message.setText(mail.message);
            mailTransporter.send(message);
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
