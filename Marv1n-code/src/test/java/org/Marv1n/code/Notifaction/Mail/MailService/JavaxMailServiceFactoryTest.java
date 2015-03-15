package org.Marv1n.code.Notifaction.Mail.MailService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JavaxMailServiceFactoryTest {

    private JavaxMailServiceFactory mailServiceFactory;
    private Protocol protocol;
    @Mock
    private MailServiceOptions mailServiceOptionsMock;
    @Mock
    private IMailTransporter mailTransporterMock;

    @Before
    public void init() {
        mailServiceFactory = new JavaxMailServiceFactory();
        mailServiceOptionsMock.port = "port";
        mailServiceOptionsMock.host = "host";
        mailServiceOptionsMock.username = "username";
        mailServiceOptionsMock.password = "password";
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTPS_ThenCreateAMailServiceSMTPS() {
        protocol = Protocol.SMTPS;

        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);

        assertTrue(createdMailService instanceof JavaxMailServiceSMTPS);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSSL_ThenCreateAMailServiceSSL() {
        protocol = Protocol.SSL;

        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);

        assertTrue(createdMailService instanceof JavaxMailServiceSSL);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTP_ThenCreateAMailServiceSMTP() {
        protocol = Protocol.SMTP;

        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptionsMock, mailTransporterMock);

        assertTrue(createdMailService instanceof JavaxMailServiceSMTP);
    }

}