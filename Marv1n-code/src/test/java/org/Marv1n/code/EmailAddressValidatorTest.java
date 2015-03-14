package org.Marv1n.code;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailAddressValidatorTest {

    private static String A_VALID_EMAIL = "exemple@exemple.com";
    private static String AN_INVALID_EMAIL = "InvalidEmail";
    private static String AN_NULL_STRING = null;

    private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();

    @Test
    public void givenEmailAddressValidator_whenValidateCalledWithValidEmail_thenReturnTrue() {
        assertTrue(emailAddressValidator.validate(A_VALID_EMAIL));
    }

    @Test
    public void givenEmailAddressValidator_whenValidateCalledWithInvalidEmail_thenReturnFalse() {
        boolean result = emailAddressValidator.validate(AN_INVALID_EMAIL);
        assertFalse(result);
    }

    @Test
    public void givenEmailAddressValidator_whenValidateCalledWithNullString_thenReturnFalse() {
        boolean result = emailAddressValidator.validate(AN_NULL_STRING);
        assertFalse(result);
    }


}