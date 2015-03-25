package infrastructure.mail;

import org.Marv1n.core.notification.mail.MailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailSenderFactoryTest {

    private JavaxMailServiceFactory mailServiceFactory;
    private Protocol protocol;
    @Mock
    private MailServiceOptions mailServiceOptionsMock;
    @Mock
    private MailTransporter mailTransporterMock;

    @Before
    public void initializeMailServiceFactory() {
        mailServiceFactory = new JavaxMailServiceFactory();
        mailServiceOptionsMock.port = "port";
        mailServiceOptionsMock.host = "host";
        mailServiceOptionsMock.username = "username";
        mailServiceOptionsMock.password = "password";
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTPS_ThenCreateAMailServiceSMTPS() {
        protocol = Protocol.SMTPS;
        MailSender createdMailSender = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);
        assertTrue(createdMailSender instanceof JavaxMailSenderSMTPS);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSSL_ThenCreateAMailServiceSSL() {
        protocol = Protocol.SSL;
        MailSender createdMailSender = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);
        assertTrue(createdMailSender instanceof JavaxMailSenderSSL);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTP_ThenCreateAMailServiceSMTP() {
        protocol = Protocol.SMTP;
        MailSender createdMailSender = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);
        assertTrue(createdMailSender instanceof JavaxMailSenderSMTP);
    }
}