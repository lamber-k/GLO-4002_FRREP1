package org.Marv1n.code.org.Marv1n.code.notifier;

import org.Marv1n.code.Person;
import org.Marv1n.code.Repository.IPersonRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


public class NotifierMail implements Notifier{

    private Session session;

    public enum Protocol {
        SMTP,
        SMTPS,
        SSL
    }

    private IPersonRepository personRepository;

    public NotifierMail(IPersonRepository personRepository, Protocol protocol, String host, String port, String username, String password) {
        this.personRepository = personRepository;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                properties.put("mail.smtp.socketFactory.port", port);
                properties.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                break;
            case SSL:
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                break;
       }
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    @Override
    public void sendNotification(Notification notification) {
        List<Person> persons = this.personRepository.FindByListOfUUID(notification.getRecieverIDs());

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("glo-noreply@gmail.com"));
            for (Person person : persons) {
                if (!person.getMailAddress().equals("")) {
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(person.getMailAddress()));
                }
            }
            message.setSubject("Reservation status notification");
            message.setText(notification.getNotifactionMessage());

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
