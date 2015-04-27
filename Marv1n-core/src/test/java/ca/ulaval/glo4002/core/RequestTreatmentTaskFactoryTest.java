package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTaskFactoryTest {

    private RequestTreatmentTaskFactory requestTreatmentTaskFactory;
    private List<Request> requestList;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private EvaluationStrategy strategyAssignationMock;
    @Mock
    private SortingRequestStrategy strategySortRequestMock;
    @Mock
    private PendingRequests pendingRequestsMock;
    @Mock
    private NotificationFactory notificationFactoryMock;
    @Mock
    private RequestRepository requestRepositoryMock;


    @Before
    public void initializeRequestTreatmentTaskFactory() {
        requestList = new ArrayList<>();
        when(pendingRequestsMock.retrieveCurrentPendingRequest()).thenReturn(requestList);
        requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignationMock, strategySortRequestMock, roomRepositoryMock, pendingRequestsMock, notificationFactoryMock, requestRepositoryMock);
    }

    @Test
    public void givenRequestTreatmentTaskFactory_WhenCreatingRequestTreatmentTask_ThenShouldCallRetrieveCurrentPendingRequestOfPendingRequests() {
        requestTreatmentTaskFactory.createTask();
        verify(pendingRequestsMock).retrieveCurrentPendingRequest();
    }

    @Test
    public void givenRequestTreatmentTaskFactory_WhenCreatingRequestTreatmentTask_ThenShouldReturnRequestTreatmentTaskObject() {
        Task requestTreatmentTask = requestTreatmentTaskFactory.createTask();
        assertEquals(RequestTreatmentTask.class, requestTreatmentTask.getClass());
    }


}