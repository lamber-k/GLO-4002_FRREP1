package org.Marv1n.code.Notification.Mail;

public class MailBuilderException extends Exception {

    private static final long serialVersionUID = 42L;
    private final String reason;

    public MailBuilderException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
