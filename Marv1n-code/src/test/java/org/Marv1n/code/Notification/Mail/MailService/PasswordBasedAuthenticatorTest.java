package org.Marv1n.code.Notification.Mail.MailService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.PasswordAuthentication;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PasswordBasedAuthenticatorTest {

    private static final String A_PASSWORD = "password";
    private static final String A_USERNAME = "bob@alice.com";
    private static final java.lang.String A_PORT = "333";
    private static final String A_HOST = "127.0.0.1";

    @Test
    public void givenThePasswordBasedAuthentication_whenCalled_ShouldReturnTheProperAuthentication() {
        MailServiceOptions mailServiceOptions = new MailServiceOptions(A_HOST, A_PORT, A_USERNAME, A_PASSWORD);
        PasswordBasedAuthenticator passwordBasedAuthenticator = new PasswordBasedAuthenticator(mailServiceOptions);

        PasswordAuthentication passwordAuthentication = passwordBasedAuthenticator.getPasswordAuthentication();

        assertEquals(A_PASSWORD, passwordAuthentication.getPassword());
        assertEquals(A_USERNAME, passwordAuthentication.getUserName());
    }
}