package core.notification.mail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MailBuilderTest {

    private static final List<MailAddress> TO_MAILS = Arrays.asList(mock(MailAddress.class), mock(MailAddress.class));
    private static final String A_STATUS = "aStatus";
    private static final String AN_IDENTIFIER = "anIdentifier";
    private static final String A_CATEGORY = "aCategory";
    @Mock
    private MailAddress fromMailMock;
    private MailBuilder mailBuilder;

    @Before
    public void initializeMailBuilder() {
        mailBuilder = new MailBuilder();
    }

    @Test
    public void givenMailBuilder_WhenCreateSuccessMail_ThenShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(A_STATUS)
                .setIdentifier(AN_IDENTIFIER)
                .setCategory(A_CATEGORY)
                .buildMail();

        assertThat(returnedMail.object, containsString(A_STATUS));
        assertThat(returnedMail.object, containsString(A_CATEGORY));
        assertThat(returnedMail.object, containsString(AN_IDENTIFIER));
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyCategory_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyIdentifier_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.setCategory(A_CATEGORY).buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyStatus_ThenShouldThrowBuilderException() throws MailBuilderException {
        mailBuilder.setCategory(A_CATEGORY).setIdentifier(AN_IDENTIFIER).buildMail();
    }

    @Test
    public void givenMailBuilder_WhenSpecifyFrom_ThenShouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setFrom(fromMailMock)
                .setIdentifier(AN_IDENTIFIER)
                .setCategory(A_CATEGORY)
                .setStatus(A_STATUS)
                .buildMail();

        assertEquals(fromMailMock, returnedMail.from);
    }

    @Test
    public void givenMailBuilder_WhenSpecifyTo_ThenShouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setTo(TO_MAILS)
                .setIdentifier(AN_IDENTIFIER)
                .setCategory(A_CATEGORY)
                .setStatus(A_STATUS)
                .buildMail();

        assertEquals(TO_MAILS, returnedMail.to);
    }
}