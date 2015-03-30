package org.Marv1n.core;

import org.Marv1n.core.request.evaluation.EvaluationNoRoomFoundException;
import org.Marv1n.core.request.evaluation.EvaluationStrategy;
import org.Marv1n.core.request.Request;
import org.Marv1n.core.request.RequestRepository;
import org.Marv1n.core.room.Room;
import org.Marv1n.core.room.RoomInsufficientSeatsException;
import org.Marv1n.core.room.RoomIsAlreadyReservedException;
import org.Marv1n.core.room.RoomRepository;
import org.Marv1n.core.request.sorting.SortingRequestStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTest {

    private List<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatment requestTreatment;
    @Mock
    private EvaluationStrategy assignerStrategyMock;
    @Mock
    private RoomRepository reservablesRepositoryMock;
    @Mock
    private SortingRequestStrategy requestSortedStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private RequestRepository requestRepositoryMock;
    @Mock
    private Room roomMock;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        when(requestRepositoryMock.findAllPendingRequest()).thenReturn(pendingRequests);
        requestTreatment = new RequestTreatment(assignerStrategyMock, requestSortedStrategyMock, reservablesRepositoryMock, requestRepositoryMock);
    }

    @Test
    public void givenPendingRequest_WhenRun_ThenShouldSortIt() {
        requestTreatment.run();

        verify(requestSortedStrategyMock).sortList(pendingRequests);
    }

    private void havingOnePendingRequest() throws EvaluationNoRoomFoundException {
        when(assignerStrategyMock.evaluateOneRequest(reservablesRepositoryMock, requestMock)).thenReturn(roomMock);
        when(requestSortedStrategyMock.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
        pendingRequests.add(requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenRun_ThenShouldEvaluateIt() throws EvaluationNoRoomFoundException {
        havingOnePendingRequest();

        requestTreatment.run();

        verify(assignerStrategyMock).evaluateOneRequest(reservablesRepositoryMock, requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldConfirmReservation() throws EvaluationNoRoomFoundException, RoomIsAlreadyReservedException, RoomInsufficientSeatsException {
        havingOnePendingRequest();

        requestTreatment.run();

        verify(roomMock).reserve(requestMock);
    }
}
