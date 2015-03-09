package org.Marv1n.code;

import java.util.UUID;

public class Person {
    private String mailAddress;
    private UUID personID;

    public Person(String mailAddress) {
        personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
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
        }
        return rhs instanceof Person && personID.equals(((Person) rhs).personID);
    }

    @Override
    public int hashCode() {
        return personID.hashCode();
    }
}
