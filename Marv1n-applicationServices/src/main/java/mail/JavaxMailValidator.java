package mail;

import core.notification.mail.EmailValidator;

import javax.mail.internet.InternetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaxMailValidator implements EmailValidator {

    private static final Logger LOGGER = Logger.getLogger(JavaxMailValidator.class.getName());

    @Override
    public boolean validateMailAddress(String address) {
        try {
            InternetAddress emailAddress = new InternetAddress(address);
            emailAddress.validate();
        } catch (Exception exception) {
            LOGGER.log(Level.FINEST, "Invalid Email Address", exception);
            return false;
        }
        return true;
    }

}
