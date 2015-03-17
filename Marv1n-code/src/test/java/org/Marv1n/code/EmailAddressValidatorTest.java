package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailAddressValidatorTest {

    private static final String A_VALID_EMAIL = "exemple@exemple.com";
    private static final String A_WRONG_EMAIL = "InvalidEmail";
    private static final String A_NULL_STRING = null;
    private static EmailAddressValidator emailAddressValidator;

    @Before
    public void initializeEmailAddressValidator() {
        emailAddressValidator = new EmailAddressValidator();
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithValidEmail_ThenReturnTrue() {
        assertTrue(emailAddressValidator.validate(A_VALID_EMAIL));
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithInvalidEmail_ThenReturnFalse() {
        boolean result = emailAddressValidator.validate(A_WRONG_EMAIL);
        assertFalse(result);
    }

    @Test
    public void givenEmailAddressValidator_WhenValidateCalledWithNullString_ThenReturnFalse() {
        boolean result = emailAddressValidator.validate(A_NULL_STRING);
        assertFalse(result);
    }
}