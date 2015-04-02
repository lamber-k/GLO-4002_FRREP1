package core;

import core.request.Request;
import core.request.evaluation.EvaluationNoRoomFoundException;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.Room;
import core.room.RoomAlreadyReservedException;
import core.room.RoomInsufficientSeatsException;
import core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTaskTest {

    private static Logger LOGGER = Logger.getLogger(RequestTreatmentTask.class.getName()); // Use the same logger as the class
    //private static TestLogHandler logHandler = new TestLogHandler();
    private List<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatmentTask requestTreatmentTask;
    @Mock
    private EvaluationStrategy assignerStrategyMock;
    @Mock
    private RoomRepository reservablesRepositoryMock;
    @Mock
    private SortingRequestStrategy requestSortedStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private Room roomMock;

    @Before
    public void initializeRequestTreatment() {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        requestTreatmentTask = new RequestTreatmentTask(assignerStrategyMock, requestSortedStrategyMock, reservablesRepositoryMock, pendingRequests);
    }

    @Test
    public void givenPendingRequest_WhenRun_ThenShouldSortIt() {
        requestTreatmentTask.run();

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

        requestTreatmentTask.run();

        verify(assignerStrategyMock).evaluateOneRequest(reservablesRepositoryMock, requestMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldConfirmReservation() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException, RoomInsufficientSeatsException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(roomMock).reserve(requestMock);
    }

    public void attachLoggingSystem() {
        //LOGGER.addHandler(logHandler);
        LOGGER.setLevel(Level.ALL);
    }

    @Test
    public void givenOnePendingRequest_WhenFailWithRoomInsufficientSeatsException_ThenShouldLogException() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException, RoomInsufficientSeatsException, IOException {
        havingOnePendingRequest();
        doThrow(EvaluationNoRoomFoundException.class).when(assignerStrategyMock).evaluateOneRequest(any(RoomRepository.class), any(Request.class));
        attachLoggingSystem();
        String EXPECTED_LOG_STREAM = "No Room Found Exception:";

        requestTreatmentTask.run();

        //assertTrue(logHandler.getLogs().contains(EXPECTED_LOG_STREAM));
        fail();
    }

    @Test
    public void givenOnePendingRequest_WhenFailWithRoomAlreadyReservedException_ThenShouldLogException() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException, RoomInsufficientSeatsException, IOException {
        havingOnePendingRequest();
        attachLoggingSystem();
        String EXPECTED_LOG_STREAM = "Already Reserved Exception:";
        doThrow(RoomAlreadyReservedException.class).when(roomMock).reserve(any(Request.class));

        requestTreatmentTask.run();

        //assertTrue(logHandler.getLogs().contains(EXPECTED_LOG_STREAM));
        fail();
    }


}
