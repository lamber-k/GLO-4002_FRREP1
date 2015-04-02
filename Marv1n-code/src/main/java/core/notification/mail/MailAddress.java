package core.notification.mail;

import javax.persistence.Embeddable;

@Embeddable
public class MailAddress {

    private final String emailAddress;

    public MailAddress(String mailAddress, EmailValidator validator) throws InvalidMailAddressException {
        if (!validator.validateMailAddress(mailAddress)) {
            throw new InvalidMailAddressException();
        }
        this.emailAddress = mailAddress;
    }

    @Override
    public int hashCode() {
        return emailAddress.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs != null && rhs instanceof MailAddress && hashCode() == rhs.hashCode();
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
