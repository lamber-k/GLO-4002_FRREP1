package ca.ulaval.glo4002.mail;

import javax.mail.MessagingException;

public class MailSendingException extends RuntimeException {

    public MailSendingException(MessagingException message) {
        super(message);
    }
}
