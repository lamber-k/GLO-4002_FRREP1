package core.notification.mail;

import javax.mail.MessagingException;

public class MailSendingException extends RuntimeException {

    public MailSendingException(MessagingException message) {
        super(message);
    }
}
