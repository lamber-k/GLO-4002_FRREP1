package core.notification.mail;

public class MailAddress {

    private final String emailAddress;

    MailAddress(String emailAddress, EmailValidator validator) throws InvalidMailAddressException {
        if (!validator.validateMailAddress(emailAddress)) {
            throw new InvalidMailAddressException();
        }
        this.emailAddress = emailAddress;
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
