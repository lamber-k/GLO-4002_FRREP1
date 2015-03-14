package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.ObjectNotFoundException;
import org.Marv1n.code.Request;
import org.Marv1n.code.RequestStatus;
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
    @Mock
    private Request requestMock;
    private UUID requestID;

    @Before
    public void setUp() throws Exception {
        requestRepository = new RequestRepositoryInMemory();
        requestID = UUID.randomUUID();
        when(requestMock.getRequestID()).thenReturn(requestID);
    }

    @Test
    public void givenEmptyRequestRepository_WhenReservableNotFound_ThenRepositoryReturnsEmptyOptional() throws Exception {
        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertFalse(result.isPresent());
    }

    @Test
    public void givenEmptyRequestRepository_WhenReservableAddedToRepository_ThenSameObjectCanBeFound() throws Exception {
        requestRepository.create(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        assertTrue(result.isPresent());
        assertEquals(requestMock, result.get());
    }

    @Test
    public void givenNotEmptyRequestRepository_WhenReservableRemoved_ThenNotFound() throws Exception {
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
    public void givenEmptyRequestRepository_WhenGetAllPendingRequest_thenReturnEmptyArray() {
        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingNoPendingRequest_WhenGetAllPendingRequest_thenReturnEmptyArray() {
        this.requestRepository.create(this.requestMock);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingAPendingRequest_WhenGetAllPendingRequest_thenReturnArrayWithThePendingRequest() {
        Request anOtherMockRequest = mock(Request.class);
        this.requestRepository.create(this.requestMock);
        this.requestRepository.create(anOtherMockRequest);
        when(anOtherMockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(1, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
    }

    @Test
    public void givenRequestRepositoryContainingMultiplePendingRequest_WhenGetAllPendingRequest_thenReturnArrayWithThePendingRequestsInOrderOfInsertion() {
        Request anOtherMockRequest = mock(Request.class);
        this.requestRepository.create(this.requestMock);
        this.requestRepository.create(anOtherMockRequest);
        when(anOtherMockRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(2, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), requestMock);
        assertEquals(pendingRequestList.get(1), anOtherMockRequest);
    }
}
