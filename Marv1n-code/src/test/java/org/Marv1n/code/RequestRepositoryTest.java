package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestRepositoryTest {
    private RequestRepository requestRepository;
    private Request mockRequest;
    private UUID mockRequestID;

    @Before
    public void setUp() throws Exception {
        this.requestRepository = new RequestRepository();
        this.mockRequest = mock(Request.class);
        this.mockRequestID = UUID.randomUUID();
        when(this.mockRequest.getRequestID()).thenReturn(this.mockRequestID);
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
}
