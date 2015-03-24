package org.Marv1n.core.Person;

import java.util.UUID;

public class Person {

    private final UUID personID;
    private final String mailAddress;
    private final boolean admin;

    public Person(String mailAddress) {
        this.personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
        this.admin = false;
    }

    public Person(String mailAddress, boolean admin) {
        personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
        this.admin = admin;
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
