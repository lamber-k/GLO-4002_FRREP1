package org.Marv1n.code.Repository.Request;

import org.Marv1n.code.ObjectNotFoundException;
import org.Marv1n.code.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestRepositoryTest {
    private RequestRepository requestRepository;
    @Mock
    private Request mockRequest;
    private UUID mockRequestID;

    @Before
    public void setUp() throws Exception {
        this.requestRepository = new RequestRepository();
        this.mockRequestID = UUID.randomUUID();
        when(this.mockRequest.getRequestID()).thenReturn(this.mockRequestID);
    }

    @Test
    public void whenReservableAddedToRepositorySameObjectCanBeFound() throws Exception {
        this.requestRepository.create(this.mockRequest);

        Optional<Request> result = this.requestRepository.findByUUID(this.mockRequestID);

        assertTrue(result.isPresent());
        assertEquals(this.mockRequest, result.get());
    }

    @Test
    public void whenReservableNotFoundRepositoryReturnsEmptyOptional() throws Exception {
        Optional<Request> result = this.requestRepository.findByUUID(this.mockRequestID);
        assertFalse(result.isPresent());
    }

    @Test
    public void whenReservableRemovedThenRepositoryReturnsEmpty() throws Exception {
        this.requestRepository.create(this.mockRequest);

        this.requestRepository.remove(this.mockRequest);

        Optional<Request> result = this.requestRepository.findByUUID(this.mockRequestID);
        assertFalse(result.isPresent());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void whenMissingReservableRemovedThenRepositoryThrowException() throws Exception {
        this.requestRepository.remove(this.mockRequest);
    }
}
