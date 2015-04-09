package ca.ulaval.glo4002.core.person;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;

public class PersonInvalidFormatException extends InvalidFormatException {

    private static final long serialVersionUID = 1L;

    public PersonInvalidFormatException(String what) {
        super(what);
    }
}
