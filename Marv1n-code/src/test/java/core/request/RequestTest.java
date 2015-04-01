package core.request;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class RequestTest {

    private static final int A_NUMBER_OF_SEATS_NEEDED = 5;
    private static final int A_PRIORITY = 2;
    private static final UUID A_UUID_REQUEST = UUID.randomUUID();
    private Request request;

    @Before
    public void initializeRequest() {
        request = new Request(A_NUMBER_OF_SEATS_NEEDED, A_PRIORITY, A_UUID_REQUEST);
    }

    @Test
    public void givenRequest_WhenComparedToIdenticalRequest_ThenReturnTrue() {
        assertTrue(request.equals(request));
    }

    @Test
    public void givenRequest_WhenComparedToDifferentRequest_ThenReturnFalse() {
        Request aDifferentRequest = new Request(A_NUMBER_OF_SEATS_NEEDED, A_PRIORITY, UUID.randomUUID());
        assertFalse(request.equals(aDifferentRequest));
    }

    @Test
    public void givenRequest_WhenComparedToDifferentObject_ThenReturnFalse() {
        Object aDifferentObject = 25;
        assertFalse(request.equals(aDifferentObject));
    }

    @Test
    public void givenRequest_WhenComparedToNull_ThenReturnFalse() {
        assertFalse(request.equals(null));
    }

    @Test
    public void givenNewRequest_WhenGetStatus_ThenShouldReturnPending() {
        assertEquals(RequestStatus.PENDING, request.getRequestStatus());
    }

    @Test
    public void givenPendingRequest_WhenAcceptRequest_ThenShouldSetStatusToAccepted() {
        request.accept();
        assertEquals(RequestStatus.ACCEPTED, request.getRequestStatus());
    }

    @Test
    public void givenPendingRequest_WhenRefuseRequest_ThenShouldSetStatusToRefused() {
        request.refuse();
        assertEquals(RequestStatus.REFUSED, request.getRequestStatus());
    }
}