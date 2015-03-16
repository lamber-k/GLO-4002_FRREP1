package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailAddressValidatorTest {

    private static final String EMAIL_VALID = "exemple@exemple.com";
    private static final String EMAIL_WRONG = "InvalidEmail";
    private static final String STRING_NULL = null;
    private static EmailAddressValidator emailAddressValidator;

    @Before
    public void initializeEmailAddressValidator() {
        emailAddressValidator = new EmailAddressValidator();
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithValidEmail_ThenReturnTrue() {
        assertTrue(emailAddressValidator.validate(EMAIL_VALID));
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithInvalidEmail_ThenReturnFalse() {
        boolean result = emailAddressValidator.validate(EMAIL_WRONG);
        assertFalse(result);
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithNullString_ThenReturnFalse() {
        boolean result = emailAddressValidator.validate(STRING_NULL);
        assertFalse(result);
    }
}