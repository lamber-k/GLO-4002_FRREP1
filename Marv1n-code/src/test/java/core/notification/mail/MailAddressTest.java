package core.notification.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailAddressTest {
    private static final String A_VALID_MAIL = "test@test.com";
    private static final String AN_INVALID_MAIL = "invalidMail";
    @Mock
    EmailValidator  emailValidatorMock;

    @Test
    public void givenAValidEmailAddress_WhenConstruct_ThenCreateIt() throws InvalidMailAddressException {
        when(emailValidatorMock.validateMailAddress(A_VALID_MAIL)).thenReturn(true);

        MailAddress address = new MailAddress(A_VALID_MAIL, emailValidatorMock);

        assertEquals(A_VALID_MAIL, address.toString());
    }

    @Test(expected = InvalidMailAddressException.class)
    public void givenAnInvalidEmailAddress_WhenConstruct_ShouldThrowInvalidEmail() throws InvalidMailAddressException {
        when(emailValidatorMock.validateMailAddress(AN_INVALID_MAIL)).thenReturn(false);
        new MailAddress(AN_INVALID_MAIL, emailValidatorMock);
    }
}
