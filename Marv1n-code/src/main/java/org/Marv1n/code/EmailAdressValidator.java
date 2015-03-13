package org.Marv1n.code;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public final class EmailAdressValidator {
    public static boolean validate(String email){
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException|NullPointerException ex ) {
            return false;
        }
        return true;
    }
}
