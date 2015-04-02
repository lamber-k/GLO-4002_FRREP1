package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;

public class InvalidRequestFormatException extends InvalidFormatException {
    InvalidRequestFormatException(String what) {
        super(what);
    }
}
