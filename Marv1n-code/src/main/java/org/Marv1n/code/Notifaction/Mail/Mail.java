package org.Marv1n.code.Notifaction.Mail;

import java.util.List;

public class Mail {

    public final String from;
    public final List<String> to;
    public final String object;
    public final String message;

    public Mail(String from, List<String> to, String object, String message) {
        this.from = from;
        this.to = to;
        this.object = object;
        this.message = message;
    }

    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Mail) {
            Mail rhsMail = (Mail) rhs;
            return (rhsMail.message.equals(message) &&
                    rhsMail.from.equals(from) &&
                    rhsMail.to.equals(to) &&
                    rhsMail.object.equals(object));
        }
        return false;
    }
}
