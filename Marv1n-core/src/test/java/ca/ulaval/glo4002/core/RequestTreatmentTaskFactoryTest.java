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
    private RoomRepository roomRepository;
    @Mock
    private EvaluationStrategy strategyAssignation;
    @Mock
    private SortingRequestStrategy strategySortRequest;
    @Mock
    private PendingRequests pendingRequests;
    @Mock
    private NotificationFactory notificationFactory;
    @Mock
    private RequestRepository requestRepository;


    @Before
    public void init() {
        requestList = new ArrayList<>();
        when(pendingRequests.retrieveCurrentPendingRequest()).thenReturn(requestList);
        requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignation, strategySortRequest, roomRepository, pendingRequests, notificationFactory, requestRepository);
    }

    @Test
    public void givenRequestTreatmentTaskFactory_WhenCreatingRequestTreatmentTask_ThenShouldCallRetrieveCurrentPendingRequestOfPendingRequests() {
        requestTreatmentTaskFactory.createTask();
        verify(pendingRequests).retrieveCurrentPendingRequest();
    }

    @Test
    public void givenRequestTreatmentTaskFactory_WhenCreatingRequestTreatmentTask_ThenShouldReturnRequestTreatmentTaskObject() {
        Task requestTreatmentTask = requestTreatmentTaskFactory.createTask();
        assertEquals(RequestTreatmentTask.class, requestTreatmentTask.getClass());
    }


}