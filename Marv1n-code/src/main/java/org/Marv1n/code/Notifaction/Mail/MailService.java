package org.Marv1n.code.Notifaction.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Rafaël on 09/03/2015.
 */
public class MailService implements IMailServiceAdapter {

    private final Protocol DEFAULT_PROTOCOL = Protocol.SMTP;
    private MailServiceOptions options;
    private Session session;

    public MailService(MailServiceOptions options) {
        this.options = options;
        this.setProtocol(DEFAULT_PROTOCOL);
    }

    public void setProtocol(Protocol protocol) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", options.host);
        properties.put("mail.smtp.port", options.port);

        switch (protocol) {
            case SMTPS:
                this.setProtocolSMTPS(properties);
                break;
            case SSL:
                this.setProtocolSSL(properties);
                break;
            default:
                this.setUnSecureSession(properties);
        }
    }

    private void setProtocolSMTPS(Properties properties) {
        properties.put("mail.smtp.socketFactory.port", options.port);
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        this.setSecureSession(properties);
    }

    private void setProtocolSSL(Properties properties) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        this.setSecureSession(properties);
    }

    private void setSecureSession(Properties properties) {
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(options.username, options.password);
            }
        });
    }

    private void setUnSecureSession(Properties properties) {
        this.session = Session.getInstance(properties, new Authenticator() {
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

    public enum Protocol {
        SMTP,
        SMTPS,
        SSL
    }

    public static class MailServiceOptions {
        public String host = "";
        public String port = "";
        public String username = "";
        public String password = "";

        public MailServiceOptions(String host, String port) {
            this.host = host;
            this.port = port;
        }

        public MailServiceOptions(String host, String port, String username, String password) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }
    }
}
