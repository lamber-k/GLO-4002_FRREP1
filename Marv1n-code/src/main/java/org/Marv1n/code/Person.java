package org.Marv1n.code;

import java.util.UUID;

public class Person {

    private final UUID personID;
    private String mailAddress;
    private boolean admin = false;

    public Person(String mailAddress) {
        this.personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
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
        return rhs != null && rhs instanceof Person && personID.equals(((Person) rhs).personID);
    }

    @Override
    public int hashCode() {
        return personID.hashCode();
    }

    public boolean isAdmin() {
        return admin;
    }
}
