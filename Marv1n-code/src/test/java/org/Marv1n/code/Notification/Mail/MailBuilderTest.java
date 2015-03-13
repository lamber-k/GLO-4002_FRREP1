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

/**
 * Created by Kevin on 13/03/2015.
 */
public class MailBuilderTest {
    private static final String RESERVABLE_NAME = "44201";
    private static final String FROM_MAIL = "from@mail.ca";
    private static final String TO_MAIL_2 = "toMail2@mail.ca";
    private static final String TO_MAIL_1 = "toMail1@mail.ca";
    private static final List<String> TO_MAILS = Arrays.asList(TO_MAIL_1, TO_MAIL_2);
    private static final int A_REQUEST_CODE = 68;
    private static final String A_MESSAGE = "Mail Builder Test Message";
    private MailBuilder mailBuilder;

    @Before
    public void initializeMailBuilder() {
        mailBuilder = new MailBuilder();
    }

    @Test
    public void givenMailBuilder_whenCreateSuccessMail_ShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.ACCEPTED)
                .setRequestID(A_REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains("acceptée"));
        assertTrue(returnedMail.object.contains(String.valueOf(A_REQUEST_CODE)));
    }

    @Test
    public void givenMailBuilder_whenCreateRefusedMail_ShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.REFUSED)
                .setRequestID(A_REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains("refusée"));
        assertTrue(returnedMail.object.contains(String.valueOf(A_REQUEST_CODE)));
    }

    @Test
    public void givenMailBuilder_whenCreateCanceledMail_ShouldFormatObjectAsSuccess() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setStatus(RequestStatus.CANCELED)
                .setRequestID(A_REQUEST_CODE)
                .buildMail();

        assertTrue(returnedMail.object.contains("annulée"));
        assertTrue(returnedMail.object.contains(String.valueOf(A_REQUEST_CODE)));
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_whenDontSpecifyRequestID_shouldThrowIDNotSet() throws MailBuilderException {
        mailBuilder.setStatus(RequestStatus.CANCELED).buildMail();
    }

    @Test(expected = MailBuilderException.class)
    public void givenMailBuilder_whenDontSpecifyRequestStatus_shouldThrowInvalidRequestStatus() throws MailBuilderException {
        mailBuilder.setRequestID(A_REQUEST_CODE).buildMail();
    }

    @Test
    public void givenMailBuilder_whenSpecifyFrom_shouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setFrom(FROM_MAIL)
                .setStatus(RequestStatus.REFUSED)
                .setRequestID(A_REQUEST_CODE)
                .buildMail();
        assertEquals(FROM_MAIL, returnedMail.from);
    }

    @Test
    public void givenMailBuilder_whenSpecifyTo_shouldSetMailProperly() throws MailBuilderException {
        Mail returnedMail = mailBuilder.setTo(TO_MAILS)
                .setStatus(RequestStatus.REFUSED)
                .setRequestID(A_REQUEST_CODE)
                .buildMail();
        assertEquals(TO_MAILS, returnedMail.to);
    }
}
