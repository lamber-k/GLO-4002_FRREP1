package org.Marv1n.code;

import java.util.UUID;

public class Person {
    private String mailAddress;
    private UUID personID;

    public Person(String mailAddress) {
        this.personID = UUID.randomUUID();
        this.mailAddress = mailAddress;
    }

    public String getMailAddress() {
        return this.mailAddress;
    }

    public UUID getID() {
        return this.personID;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) {
            return false;
        }
        return rhs instanceof Person && this.personID.equals(((Person) rhs).personID);
    }
}
