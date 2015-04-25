package ca.ulaval.glo4002.applicationServices.mail;

import javax.mail.MessagingException;

public class MailSendingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MailSendingException(MessagingException message) {
        super(message);
    }
}
