package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;

public class InvalidRequestFormatException extends InvalidFormatException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestFormatException(String what) {
        super(what);
    }
}
