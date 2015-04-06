package ca.ulaval.glo4002.mail;

import ca.ulaval.glo4002.core.notification.mail.EmailValidator;

import javax.mail.internet.InternetAddress;

public class JavaxMailValidator implements EmailValidator {

    @Override
    public boolean validateMailAddress(String address) {
        try {
            InternetAddress emailAddress = new InternetAddress(address);
            emailAddress.validate();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
