package infrastructure.mail;

import org.Marv1n.core.notification.mail.Mail;
import org.Marv1n.core.notification.mail.MailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JavaxMailSender implements MailSender {

    private static final String CONFIG_FILE_NAME = "config/mail.properties";
    private Session session;
    private MailTransporter mailTransporter;

    public JavaxMailSender(MailTransporter mailTransporter) throws IOException {
        this.mailTransporter = mailTransporter;
        Properties properties = retreiveProperties();
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        if(username == null || password == null) {
            session = Session.getDefaultInstance(properties);
        } else {
            session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });
        }
    }

    private Properties retreiveProperties() throws IOException {
        Properties properties = new Properties();
        InputStream configFile = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);

        if (configFile != null) {
            properties.load(configFile);
        } else {
            throw new FileNotFoundException("Property file '" + CONFIG_FILE_NAME +"'.");
        }

        return properties;
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
            mailTransporter.send(message);
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
