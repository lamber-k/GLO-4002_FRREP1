package org.Marv1n.code.Notifaction.Mail.MailService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MailSerciceFactoryTest {

    private MailSerciceFactory mailServiceFactory;
    private Protocol protocol;
    @Mock
    private MailServiceOptions mailServiceOptions;

    @Before
    public void init() {
        mailServiceFactory = new MailSerciceFactory();
        mailServiceOptions.port = "port";
        mailServiceOptions.host = "host";
        mailServiceOptions.username = "username";
        mailServiceOptions.password = "password";
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