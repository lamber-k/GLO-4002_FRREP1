package org.Marv1n.code.Notification.Mail;

public class MailBuilderException extends Exception {

    private final String reason;

    public MailBuilderException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
