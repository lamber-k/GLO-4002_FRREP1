package org.Marv1n.code.Notifaction.Mail;

/**
 * Created by Kevin on 13/03/2015.
 */
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
