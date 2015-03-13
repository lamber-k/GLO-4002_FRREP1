package org.Marv1n.code;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailAdressValidatorTest {

    private static String A_VALID_EMAIL = "exemple@exemple.com";
    private static String AN_INVALID_EMAIL = "InvalidEmail";
    private static String AN_NULL_STRING = null;

    private static EmailAdressValidator emailAdressValidator = new EmailAdressValidator();

    @Test
    public void givenEmailAdressValidator_whenValidateCalledWithValidEmail_thenReturnTrue() {
        assertTrue(emailAdressValidator.validate(A_VALID_EMAIL));
    }

    @Test
    public void givenEmailAdressValidator_whenValidateCalledWithInvalidEmail_thenReturnFalse() {
        boolean result = emailAdressValidator.validate(AN_INVALID_EMAIL);
        assertFalse(result);
    }

    @Test
    public void givenEmailAdressValidator_whenValidateCalledWithNullString_thenReturnFalse() {
        boolean result = emailAdressValidator.validate(AN_NULL_STRING);
        assertFalse(result);
    }


}