package core.notification.mail;

import javax.persistence.Embeddable;

@Embeddable
public class MailAddress {

    private final String mailAddress;

    public MailAddress(String mailAddress, EmailValidator validator) throws InvalidMailAddressException {
        if (!validator.validateMailAddress(mailAddress)) {
            throw new InvalidMailAddressException();
        }
        this.mailAddress = mailAddress;
    }

    @Override
    public int hashCode() {
        return mailAddress.hashCode();
    }

    @Override
    public String toString() {
        return mailAddress;
    }
}
