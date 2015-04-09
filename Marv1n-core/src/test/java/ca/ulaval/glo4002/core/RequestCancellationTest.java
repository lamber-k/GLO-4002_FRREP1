package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestCancellationTest {
    private static final UUID AN_UUID = UUID.randomUUID();
    @Mock
    PendingRequests pendingRequestsMock;
    @Mock
    RequestRepository requestRepositoryMock;
    private RequestCancellation requestCancellation;

    @Before
    public void initializeRequestCancellation() {
        this.requestCancellation = new RequestCancellation(pendingRequestsMock, requestRepositoryMock);
    }

    @Test
    public void givenRequestCancellation_WhenCancellingRequest_ShouldCallPendingRequest() throws InvalidFormatException {
        requestCancellation.cancelRequestByUUID(AN_UUID);

        verify(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock);
    }

    @Test
    public void givenRequestCancellation_WhenCancellingRequestTreated_ShouldCallRequestRepository() throws InvalidFormatException, RequestNotFoundException {
        Mockito.doThrow(ObjectNotFoundException.class).when(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock);

        requestCancellation.cancelRequestByUUID(AN_UUID);

        verify(requestRepositoryMock).findByUUID(AN_UUID);
    }

    @Test (expected = ObjectNotFoundException.class)
    public void givenRequestCancellation_WhenCancellingInvalidRequest_ShouldThrowObjectNotFoundException() throws InvalidFormatException, RequestNotFoundException {
        Mockito.doThrow(ObjectNotFoundException.class).when(pendingRequestsMock).cancelPendingRequest(AN_UUID, requestRepositoryMock);
        Mockito.doThrow(ObjectNotFoundException.class).when(requestRepositoryMock).findByUUID(AN_UUID);

        this.requestCancellation.cancelRequestByUUID(AN_UUID);
    }
}
