package core.notification.mail;

public class MailAddress {

    private final String mailAddress;

    MailAddress(String mailAddress, EmailValidator validator) throws InvalidMailAddressException {
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
