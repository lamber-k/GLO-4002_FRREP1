package ca.ulaval.glo4002.core.person;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Person {

    @Id
    private final UUID personID;
    private final String email;
    private final boolean admin;

    public Person(String email) {
        this.personID = UUID.randomUUID();
        this.email = email;
        this.admin = false;
    }

    public Person(String email, boolean admin) {
        this.personID = UUID.randomUUID();
        this.email = email;
        this.admin = admin;
    }

    public String getMailAddress() {
        return email;
    }

    public UUID getID() {
        return personID;
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs != null && rhs instanceof Person && hashCode() == rhs.hashCode();
    }

    @Override
    public int hashCode() {
        return personID.hashCode();
    }

    public boolean isAdmin() {
        return admin;
    }
}
