package ca.ulaval.glo4002.applicationServices.mail;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class JavaxMailValidatorTest {

    private static final String A_VALID_MAIL = "test@test.com";
    private static final String AN_INVALID_MAIL = "invalidMail";
    private JavaxMailValidator javaxMailValidator;

    @Before
    public void initializeValidator() {
        javaxMailValidator = new JavaxMailValidator();
    }

    @Test
    public void givenAValidEmailAddress_WhenValidate_ThenReturnTrue() {
        assertTrue(javaxMailValidator.validateMailAddress(A_VALID_MAIL));
    }

    @Test
    public void givenAnInvalidEmailAddress_WhenValidate_ThenReturnFalse() {
        assertFalse(javaxMailValidator.validateMailAddress(AN_INVALID_MAIL));
    }
}
