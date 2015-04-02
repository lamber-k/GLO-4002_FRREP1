package core.request;

import core.persistence.InvalidFormatException;

public class InvalidRequestFormatException extends InvalidFormatException {
    InvalidRequestFormatException(String what) {
        super(what);
    }
}
