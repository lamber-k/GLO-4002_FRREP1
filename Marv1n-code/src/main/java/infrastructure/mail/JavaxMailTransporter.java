package infrastructure.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class JavaxMailTransporter {

    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
}
