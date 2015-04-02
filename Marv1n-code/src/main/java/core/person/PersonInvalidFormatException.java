package core.person;

import core.persistence.InvalidFormatException;

public class PersonInvalidFormatException extends InvalidFormatException {
    public PersonInvalidFormatException(String what) {
        super(what);
    }
}
