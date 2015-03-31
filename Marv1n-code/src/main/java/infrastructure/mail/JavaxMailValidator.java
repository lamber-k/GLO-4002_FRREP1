package infrastructure.mail;

import core.notification.mail.EmailValidator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class JavaxMailValidator implements EmailValidator {

    @Override
    public boolean validateMailAddress(String address) {
        try {
            InternetAddress emailAddress = new InternetAddress(address);
            emailAddress.validate();
        } catch (AddressException | NullPointerException exception) {
            return false;
        }
        return true;
    }

}
