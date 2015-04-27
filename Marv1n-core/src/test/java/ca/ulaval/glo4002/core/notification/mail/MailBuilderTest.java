package ca.ulaval.glo4002.core.notification.mail;

import ca.ulaval.glo4002.core.request.RequestStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class MailBuilderTest {

    private static final List<String> TO_MAILS = Arrays.asList("to@mail.com", "another.to@mail.com");
    private static final RequestStatus A_STATUS = RequestStatus.ACCEPTED;
    private static final UUID AN_IDENTIFIER = UUID.randomUUID();
    private static final String A_REASON = "aReason";
    private static final String FROM_MAIL = "from@mail.com";
    private MailBuilder mailBuilder;
    private Mail aMail;

    @Before
    public void initializeMailBuilder() {
        mailBuilder = new MailBuilder();
    }

    @Test
    public void givenMailBuilder_WhenCreateSuccessMail_ThenShouldFormatObjectAsSuccess() throws MailBuilderException {
        buildAMail();

        assertThat(aMail.getObject(), containsString(A_STATUS.toString()));
        assertThat(aMail.getObject(), containsString(AN_IDENTIFIER.toString()));
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyCategory_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyIdentifier_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.setReason(A_REASON).buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyStatus_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.setReason(A_REASON).setIdentifier(AN_IDENTIFIER).buildMail();
    }

    private void buildAMail() throws MailBuilderException {
        aMail = mailBuilder.setFrom(FROM_MAIL)
                .setTo(TO_MAILS)
                .setIdentifier(AN_IDENTIFIER)
                .setReason(A_REASON)
                .setStatus(A_STATUS)
                .buildMail();
    }

    @Test
    public void givenMailBuilder_WhenSpecifyFrom_ThenShouldSetMailProperly() throws MailBuilderException {
        buildAMail();
        assertEquals(FROM_MAIL, aMail.getFrom());
    }

    @Test
    public void givenMailBuilder_WhenSpecifyTo_ThenShouldSetMailProperly() throws MailBuilderException {
        buildAMail();
        assertEquals(TO_MAILS, aMail.getTo());
    }

    @Test
    public void givenMailBuilder_WhenBuildMail_ThenMailMessageShouldContainReason() throws MailBuilderException {
        buildAMail();
        assertTrue(aMail.getMessage().toString().contains(A_REASON.toString()));
    }

    @Test
    public void givenMailFactory_WhenBuildMail_ThenMailMessageShouldContainStatus() throws MailBuilderException {
        buildAMail();
        assertTrue(aMail.getMessage().toString().contains(A_STATUS.toString()));
    }

    @Test
    public void givenMailFactory_WhenBuildMail_ThenMailMessageShouldContainID() throws MailBuilderException {
        buildAMail();
        assertTrue(aMail.getMessage().toString().contains(AN_IDENTIFIER.toString()));
    }
}
