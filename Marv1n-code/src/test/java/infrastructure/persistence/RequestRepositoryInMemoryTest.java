package infrastructure.persistence;


import core.ObjectNotFoundException;
import core.request.Request;
import core.request.RequestStatus;
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
    public void givenEmptyRequestRepositoryInMemory_WhenReservableNotFound_ThenRepositoryReturnEmptyOptional() throws Exception {
        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertFalse(result.isPresent());
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenCreateReservable_ThenSameObjectCanBeFound() throws Exception {
        requestRepository.persist(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertTrue(result.isPresent());
        assertEquals(requestMock, result.get());
    }

    @Test
    public void givenNotEmptyRequestRepositoryInMemory_WhenRemoveReservable_ThenNotFound() throws Exception {
        requestRepository.persist(requestMock);

        requestRepository.remove(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertFalse(result.isPresent());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenMissingReservableRemoved_ThenRepositoryThrowException() throws Exception {
        requestRepository.remove(requestMock);
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingNoPendingRequest_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        requestRepository.persist(requestMock);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryInMemoryContainingAPendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequest() {
        Request anotherRequestMock = mock(Request.class);
        requestRepository.persist(requestMock);
        requestRepository.persist(anotherRequestMock);
        when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        assertEquals(1, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
    }

    @Test
    public void givenRequestRepositoryInMemoryContainingMultiplePendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequestsInOrderOfInsertion() {
        Request anotherRequestMock = mock(Request.class);
        requestRepository.persist(requestMock);
        requestRepository.persist(anotherRequestMock);
        when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        assertEquals(2, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
        assertEquals(pendingRequestList.get(1), anotherRequestMock);
    }
}