package core.notification.mail;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MailAddress {

    @Id private final String mailAddress;

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
