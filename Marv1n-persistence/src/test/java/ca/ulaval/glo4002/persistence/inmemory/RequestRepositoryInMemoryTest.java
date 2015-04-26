package ca.ulaval.glo4002.persistence.inmemory;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
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

    @Test(expected = RequestNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenReservableNotFound_ThenThrowNotFoundRequestException() throws RequestNotFoundException {
        requestRepository.findByUUID(requestID);
    }

    @Test
    public void givenEmptyRequestRepositoryInMemory_WhenCreateReservable_ThenSameObjectCanBeFound() throws Exception {
        requestRepository.persist(requestMock);

        Request result = requestRepository.findByUUID(requestID);
        Assert.assertEquals(requestMock, result);
    }

    @Test(expected = RequestNotFoundException.class)
    public void givenNotEmptyRequestRepositoryInMemory_WhenRemoveReservable_ThenNotFound() throws RequestNotFoundException, ObjectNotFoundException {
        requestRepository.persist(requestMock);

        requestRepository.remove(requestMock);

        requestRepository.findByUUID(requestID);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenEmptyRequestRepositoryInMemory_WhenMissingReservableRemoved_ThenRepositoryThrowException() throws Exception {
        requestRepository.remove(requestMock);
    }
}