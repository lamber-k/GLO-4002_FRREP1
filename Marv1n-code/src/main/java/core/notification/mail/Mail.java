package core.notification.mail;

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

    @Override
    public int hashCode() {
        return 3 * message.hashCode() + 5 * from.hashCode() + 7 * to.hashCode() + 11 * object.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Mail) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }
}
