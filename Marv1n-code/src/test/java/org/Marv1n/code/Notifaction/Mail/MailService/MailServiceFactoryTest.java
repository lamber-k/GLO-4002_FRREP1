package org.Marv1n.code.Notifaction.Mail.MailService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

public class MailServiceFactoryTest {

    private MailServiceFactory mailServiceFactory;
    private Protocol protocol;
    private MailServiceOptions mailServiceOptions;

    @Before
    public void initializeMailServiceFactory() {
        mailServiceFactory = new MailServiceFactory();
        mailServiceOptions = new MailServiceOptions("port", "host", "username", "password");
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTPS_ThenCreateAMailServiceSMTPS() {
        protocol = Protocol.SMTPS;
        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptions);
        assertTrue(createdMailService instanceof MailServiceSMTPS);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSSL_ThenCreateAMailServiceSSL() {
        protocol = Protocol.SSL;
        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptions);
        assertTrue(createdMailService instanceof MailServiceSSL);
    }

    @Test
    public void givenMailServiceFactory_WhenProtocolSMTP_ThenCreateAMailServiceSMTP() {
        protocol = Protocol.SMTP;
        IMailService createdMailService = mailServiceFactory.createMailService(protocol, mailServiceOptions);
        assertTrue(createdMailService instanceof MailServiceSMTP);
    }
}