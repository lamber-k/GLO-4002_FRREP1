package ca.ulaval.glo4002.core.request;

import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RequestTest {

    public static final String REASON_OF_REFUSE = "ReasonOfRefuse";
    private static final int A_NUMBER_OF_SEATS_NEEDED = 5;
    private static final int A_PRIORITY = 2;
    @Mock
    private Room roomMock;
    @Mock
    private Person A_PERSON;
    private Request request;

    @Before
    public void initializeRequest() {
        request = new Request(A_NUMBER_OF_SEATS_NEEDED, A_PRIORITY, A_PERSON);
    }

    @Test
    public void givenRequest_WhenComparedToIdenticalRequest_ThenReturnTrue() {
        assertTrue(request.equals(request));
    }

    @Test
    public void givenRequest_WhenComparedToDifferentRequest_ThenReturnFalse() {
        Request aDifferentRequest = new Request(A_NUMBER_OF_SEATS_NEEDED, A_PRIORITY, A_PERSON);
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
    public void givenPendingRequest_WhenReserveValidRoom_ShouldBookRoomThenShouldSetStatusToAccepted() throws RoomAlreadyReservedException {
        request.reserve(roomMock);

        verify(roomMock).book(request);
        assertEquals(RequestStatus.ACCEPTED, request.getRequestStatus());
    }

    @Test
    public void givenPendingRequest_WhenReserveUnvalidRoom_ShouldSetStatusToRefused() throws RoomAlreadyReservedException {
        Mockito.doThrow(RoomAlreadyReservedException.class).when(roomMock).book(request);
        request.reserve(roomMock);

        verify(roomMock).book(request);
        assertEquals(RequestStatus.REFUSED, request.getRequestStatus());
    }

    @Test
    public void givenPendingRequest_WhenRefuseRequest_ThenShouldSetStatusToRefused() {
        request.refuse(REASON_OF_REFUSE);
        assertEquals(RequestStatus.REFUSED, request.getRequestStatus());
    }

    @Test
    public void givenPendingRequest_WhenCancelRequest_ShouldSetStatusToCancel() {
        request.cancel();

        assertEquals(RequestStatus.CANCELED, request.getRequestStatus());
    }

    @Test
    public void givenAcceptedRequest_WhenCancelRequest_ShouldUnbookRoom() {
        request.reserve(roomMock);

        request.cancel();

        verify(roomMock).unbook();
    }
}