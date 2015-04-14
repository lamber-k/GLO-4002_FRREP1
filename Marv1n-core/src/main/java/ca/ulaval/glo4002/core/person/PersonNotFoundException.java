package ca.ulaval.glo4002.core.person;

public class PersonNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersonNotFoundException() {

    }

    public PersonNotFoundException(Exception exception) {
        super(exception);
    }
}
