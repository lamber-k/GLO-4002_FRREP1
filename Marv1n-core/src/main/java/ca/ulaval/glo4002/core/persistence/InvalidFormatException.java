package ca.ulaval.glo4002.core.persistence;

public class InvalidFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidFormatException(String what) {
        super(what);
    }
}
