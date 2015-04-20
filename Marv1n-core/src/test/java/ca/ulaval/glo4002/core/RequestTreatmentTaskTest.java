package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationNoRoomFoundException;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTaskTest {

    private List<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatmentTask requestTreatmentTask;
    @Mock
    private EvaluationStrategy assignerStrategyMock;
    @Mock
    private RoomRepository roomRepositoryMock;
    @Mock
    private SortingRequestStrategy requestSortingStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private Room roomMock;
    @Mock
    private NotificationFactory notificationFactoryMock;
    @Mock
    private Notification notificationMock;
    @Mock
    private RequestRepository requestRepositoryMock;

    @Before
    public void initializeRequestTreatment() throws InterruptedException {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        requestTreatmentTask = new RequestTreatmentTask(assignerStrategyMock, requestSortingStrategyMock, roomRepositoryMock, pendingRequests, notificationFactoryMock, requestRepositoryMock);
        when(notificationFactoryMock.createNotification(requestMock)).thenReturn(notificationMock);
    }

    @Test
    public void givenPendingRequest_WhenRun_ThenShouldSortIt() {
        requestTreatmentTask.run();

        verify(requestSortingStrategyMock).sortList(pendingRequests);
    }

    private void havingOnePendingRequest() throws EvaluationNoRoomFoundException {
        when(assignerStrategyMock.evaluateOneRequest(roomRepositoryMock, requestMock)).thenReturn(roomMock);
        when(requestSortingStrategyMock.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        pendingRequests.add(requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenRun_ThenShouldEvaluateIt() throws EvaluationNoRoomFoundException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(assignerStrategyMock).evaluateOneRequest(roomRepositoryMock, requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldConfirmReservation() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(requestMock).reserve(roomMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldUpdateRoomRepository() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(roomRepositoryMock).persist(roomMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldCreateNotification() {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(notificationFactoryMock).createNotification(requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldAnnounceWithNotificationCreateByFactory() {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(notificationMock).announce();
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldUpdateRequestRepository() {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(requestRepositoryMock).persist(requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenNoRoomFound_ThenShouldRefuseReservation() {
        pendingRequests.add(requestMock);
        when(requestSortingStrategyMock.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        doThrow(EvaluationNoRoomFoundException.class).when(assignerStrategyMock).evaluateOneRequest(roomRepositoryMock, requestMock);

        requestTreatmentTask.run();

        verify(requestMock).refuse(any(String.class));
    }

    @Test
    public void givenOnePendingRequest_WhenRoomAlreadyReservedAtReservation_ThenCallReserve() throws RoomAlreadyReservedException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(requestMock).reserve(roomMock);
    }


}
