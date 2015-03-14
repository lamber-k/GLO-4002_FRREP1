package org.Marv1n.code.Notification.Mail;

import org.Marv1n.code.Notifaction.Mail.Mail;
import org.Marv1n.code.Notifaction.Mail.MailBuilder;
import org.Marv1n.code.Notifaction.Mail.MailBuilderException;
import org.Marv1n.code.RequestStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MailBuilderTest {

    private static final String FROM_MAIL = "from@mail.ca";
    private static final String TO_MAIL_1 = "toMail1@mail.ca";
    private static final String TO_MAIL_2 = "toMail2@mail.ca";
    private static final List<String> TO_MAILS = Arrays.asList(TO_MAIL_1, TO_MAIL_2);
    private static final int REQUEST_CODE = 68;
    private MailBuilder mailBuilder;

    @Before
    public void initializeMailBuilder() {
        mailBuilder = new MailBuilder();
    }

    @Test
    public void givenMailBuilder_WhenCreateSuccessMail_ThenShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.ACCEPTED)
                .setRequestID(REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains(MailBuilder.MAIL_OBJECT_STATUS_ACCEPTED));
        assertTrue(returnedMail.object.contains(String.valueOf(REQUEST_CODE)));
    }

    @Test
    public void givenMailBuilder_WhenCreateRefusedMail_ThenShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.REFUSED)
                .setRequestID(REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains(MailBuilder.MAIL_OBJECT_STATUS_REFUSED));
        assertTrue(returnedMail.object.contains(String.valueOf(REQUEST_CODE)));
    }

    @Test
    public void givenMailBuilder_WhenCreateCanceledMail_ThenShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.CANCELED)
                .setRequestID(REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains(MailBuilder.MAIL_OBJECT_STATUS_CANCELED));
        assertTrue(returnedMail.object.contains(String.valueOf(REQUEST_CODE)));
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyRequestID_ThenShouldThrowIDNotSet() throws MailBuilderException {
        mailBuilder.setStatus(RequestStatus.CANCELED).buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_WhenDoNotSpecifyRequestStatus_ThenShouldThrowInvalidRequestStatus() throws MailBuilderException {
        mailBuilder.setRequestID(REQUEST_CODE).buildMail();
    }

    @Test
    public void givenMailBuilder_WhenSpecifyFrom_ThenShouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setFrom(FROM_MAIL)
                .setStatus(RequestStatus.REFUSED)
                .setRequestID(REQUEST_CODE)
                .buildMail();

        assertEquals(FROM_MAIL, returnedMail.from);
    }

    @Test
    public void givenMailBuilder_WhenSpecifyTo_ThenShouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setTo(TO_MAILS)
                .setStatus(RequestStatus.REFUSED)
                .setRequestID(REQUEST_CODE)
                .buildMail();

        assertEquals(TO_MAILS, returnedMail.to);
    }
}
