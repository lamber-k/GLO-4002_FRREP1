package org.Marv1n.code.Notifaction.Mail.MailService;

import org.Marv1n.code.Notifaction.Mail.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailServiceSSL implements IMailService {
    private MailServiceOptions options;
    private Session session;

    public MailServiceSSL(MailServiceOptions options) {
        this.options = options;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(options.username, options.password);
            }
        });
    }


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

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
