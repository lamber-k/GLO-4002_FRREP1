package core.person;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.UUID;

public class Person {

    private final UUID personID;
    private final String mailAddress;
    private final boolean admin;

    public Person(String mailAddress) throws AddressException {
        if (!validate(mailAddress)) {
            throw new AddressException();
        }
        this.personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
        this.admin = false;
    }

    public Person(String mailAddress, boolean admin) throws AddressException {
        if (!validate(mailAddress)) {
            throw new AddressException();
        }
        this.personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
        this.admin = admin;
    }

    private static boolean validate(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException | NullPointerException exception) {
            return false;
        }
        return true;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public UUID getID() {
        return personID;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        } else if (rhs instanceof Person) {
            return hashCode() == rhs.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return personID.hashCode();
    }

    public boolean isAdmin() {
        return admin;
    }
}
