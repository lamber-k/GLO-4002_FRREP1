package core.notification.mail;

public class MailSendingException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public MailSendingException(String message) {
        super(message);
    }
}
