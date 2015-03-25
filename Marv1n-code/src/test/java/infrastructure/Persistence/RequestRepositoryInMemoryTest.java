package infrastructure.persistence;

import org.Marv1n.core.ObjectNotFoundException;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryInMemoryTest {

    private RequestRepositoryInMemory requestRepository;
    private UUID requestID;
    @Mock
    private Request requestMock;

    @Before
    public void initializeRequestRepositoryInMemory() throws Exception {
        requestRepository = new RequestRepositoryInMemory();
        requestID = UUID.randomUUID();
        when(requestMock.getRequestID()).thenReturn(requestID);
    }

    @Test
    public void givenEmptyRequestRepository_WhenReservableNotFound_ThenRepositoryReturnEmptyOptional() throws Exception {
        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertFalse(result.isPresent());
    }

    @Test
    public void givenEmptyRequestRepository_WhenCreateReservable_ThenSameObjectCanBeFound() throws Exception {
        requestRepository.create(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertTrue(result.isPresent());
        assertEquals(requestMock, result.get());
    }

    @Test
    public void givenNotEmptyRequestRepository_WhenRemoveReservable_ThenNotFound() throws Exception {
        requestRepository.create(requestMock);

        requestRepository.remove(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertFalse(result.isPresent());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenEmptyRequestRepository_WhenMissingReservableRemoved_ThenRepositoryThrowException() throws Exception {
        requestRepository.remove(requestMock);
    }

    @Test
    public void givenEmptyRequestRepository_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        List<Request> pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingNoPendingRequest_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        this.requestRepository.create(this.requestMock);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);

        List<Request> pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingAPendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequest() {
        Request anotherRequestMock = mock(Request.class);
        this.requestRepository.create(this.requestMock);
        this.requestRepository.create(anotherRequestMock);
        when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(1, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
    }

    @Test
    public void givenRequestRepositoryContainingMultiplePendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequestsInOrderOfInsertion() {
        Request anotherRequestMock = mock(Request.class);
        this.requestRepository.create(this.requestMock);
        this.requestRepository.create(anotherRequestMock);
        when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(2, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
        assertEquals(pendingRequestList.get(1), anotherRequestMock);
    }
}
