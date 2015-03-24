package org.Marv1n.core;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public final class EmailAddressValidator {

    public static boolean validate(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException | NullPointerException exception) {
            return false;
        }
        return true;
    }
}
