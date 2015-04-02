package ca.ulaval.glo4002.core.person;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;

public class PersonInvalidFormatException extends InvalidFormatException {
    public PersonInvalidFormatException(String what) {
        super(what);
    }
}
