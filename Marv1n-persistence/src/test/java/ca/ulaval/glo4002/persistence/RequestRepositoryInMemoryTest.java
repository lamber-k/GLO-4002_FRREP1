package ca.ulaval.glo4002.persistence;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Mockito.when(requestMock.getRequestID()).thenReturn(requestID);
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenReservableNotFound_ThenRepositoryReturnEmptyOptional() throws Exception {
        Optional<Request> result = requestRepository.findByUUID(requestID);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenCreateReservable_ThenSameObjectCanBeFound() throws Exception {
        requestRepository.persist(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(requestMock, result.get());
    }

    @Test
    public void givenNotEmptyRequestRepositoryInMemory_WhenRemoveReservable_ThenNotFound() throws Exception {
        requestRepository.persist(requestMock);

        requestRepository.remove(requestMock);

        Optional<Request> result = requestRepository.findByUUID(requestID);
        Assert.assertFalse(result.isPresent());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenMissingReservableRemoved_ThenRepositoryThrowException() throws Exception {
        requestRepository.remove(requestMock);
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryContainingNoPendingRequest_WhenGetAllPendingRequest_ThenReturnEmptyArray() {
        requestRepository.persist(requestMock);
        Mockito.when(requestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertEquals(0, pendingRequestList.size());
    }

    @Test
    public void givenRequestRepositoryInMemoryContainingAPendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequest() {
        Request anotherRequestMock = Mockito.mock(Request.class);
        requestRepository.persist(requestMock);
        requestRepository.persist(anotherRequestMock);
        Mockito.when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.ACCEPTED);
        Mockito.when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertEquals(1, pendingRequestList.size());
        Assert.assertEquals(pendingRequestList.get(0), requestMock);
    }

    @Test
    public void givenRequestRepositoryInMemoryContainingMultiplePendingRequest_WhenGetAllPendingRequest_ThenReturnArrayWithThePendingRequestsInOrderOfInsertion() {
        Request anotherRequestMock = Mockito.mock(Request.class);
        requestRepository.persist(requestMock);
        requestRepository.persist(anotherRequestMock);
        Mockito.when(anotherRequestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);
        Mockito.when(requestMock.getRequestStatus()).thenReturn(RequestStatus.PENDING);

        List<Request> pendingRequestList = requestRepository.findAllPendingRequest();

        Assert.assertEquals(2, pendingRequestList.size());
        Assert.assertEquals(pendingRequestList.get(0), requestMock);
        Assert.assertEquals(pendingRequestList.get(1), anotherRequestMock);
    }
}