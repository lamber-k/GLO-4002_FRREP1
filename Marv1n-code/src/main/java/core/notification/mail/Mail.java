package core.notification.mail;

import java.util.List;

public class Mail {

    public final MailAddress from;
    public final List<MailAddress> to;
    public final String object;
    public final String message;

    public Mail(MailAddress from, List<MailAddress> to, String object, String message) {
        this.from = from;
        this.to = to;
        this.object = object;
        this.message = message;
    }

    @Override
    public int hashCode() {
        return 3 * message.hashCode() + 5 * from.hashCode() + 7 * to.hashCode() + 11 * object.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs != null && rhs instanceof Mail && hashCode() == rhs.hashCode();
    }
}
