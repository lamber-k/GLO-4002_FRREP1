package ca.ulaval.glo4002.Marv1n.applicationServices.mail;

import ca.ulaval.glo4002.Marv1n.applicationServices.TestUtilitary.TestLogHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class JavaxMailValidatorTest {

    private static final String A_VALID_MAIL = "test@test.com";
    private static final String AN_INVALID_MAIL = "invalidMail";
    private static final Logger LOGGER = Logger.getLogger(JavaxMailValidator.class.getName());
    private static TestLogHandler logHandler = new TestLogHandler();
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

    public void attachLoggingSystem() {
        LOGGER.addHandler(logHandler);
        LOGGER.setLevel(Level.ALL);
    }

    @Test
    public void givenAnInvalidEmailAddress_WhenValidate_ThenExceptionLanchedAreLogged() {
        attachLoggingSystem();
        String EXPECTED_LOG_STREAM = "Invalid Email Address";

        javaxMailValidator.validateMailAddress(AN_INVALID_MAIL);

        assertTrue(logHandler.getLogs().contains(EXPECTED_LOG_STREAM));
    }

}
