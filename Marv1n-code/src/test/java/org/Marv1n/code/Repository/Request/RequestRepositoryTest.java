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
public class RequestRepositoryTest {

    private RequestRepository requestRepository;
    @Mock
    private Request mockRequest;
    private UUID mockRequestID;

    @Before
    public void setUp() throws Exception {
        requestRepository = new RequestRepository();
        mockRequestID = UUID.randomUUID();
        when(mockRequest.getRequestID()).thenReturn(mockRequestID);
    }

    @Test
    public void whenReservableAddedToRepositorySameObjectCanBeFound() throws Exception {
        requestRepository.create(mockRequest);

        Optional<Request> result = requestRepository.findByUUID(mockRequestID);

        assertTrue(result.isPresent());
        assertEquals(mockRequest, result.get());
    }

    @Test
    public void whenReservableNotFoundRepositoryReturnsEmptyOptional() throws Exception {
        Optional<Request> result = requestRepository.findByUUID(mockRequestID);
        assertFalse(result.isPresent());
    }

    @Test
    public void whenReservableRemovedThenRepositoryReturnsEmpty() throws Exception {
        requestRepository.create(mockRequest);

        requestRepository.remove(mockRequest);

        Optional<Request> result = requestRepository.findByUUID(mockRequestID);
        assertFalse(result.isPresent());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void whenMissingReservableRemovedThenRepositoryThrowException() throws Exception {
        requestRepository.remove(mockRequest);
    }

    @Test
    public void givenEmptyRequestRepository_whenGetAllPendingRequest_thenReturnEmptyArray() {
        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingNoPendingRequest_whenGetAllPendingRequest_thenReturnEmptyArray() {
        this.requestRepository.create(this.mockRequest);
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingAPendingRequest_whenGetAllPendingRequest_thenReturnArrayWithThePendingRequest() {
        Request anOtherMockRequest = mock(Request.class);
        this.requestRepository.create(this.mockRequest);
        this.requestRepository.create(anOtherMockRequest);
        when(anOtherMockRequest.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(1, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), mockRequest);
    }

    @Test
    public void givenRequestRepositoryContainingMultiplePendingRequest_whenGetAllPendingRequest_thenReturnArrayWithThePendingRequestsInOrderOfInsertion() {
        Request anOtherMockRequest = mock(Request.class);
        this.requestRepository.create(this.mockRequest);
        this.requestRepository.create(anOtherMockRequest);
        when(anOtherMockRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        when(mockRequest.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List pendingRequestList = this.requestRepository.findAllPendingRequest();

        assertEquals(2, pendingRequestList.size());
        assertEquals(pendingRequestList.get(0), mockRequest);
        assertEquals(pendingRequestList.get(1), anOtherMockRequest);
    }
}
