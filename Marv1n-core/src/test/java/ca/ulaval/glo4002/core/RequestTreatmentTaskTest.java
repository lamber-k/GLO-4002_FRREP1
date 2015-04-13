package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.mail.MailSender;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationNoRoomFoundException;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomInsufficientSeatsException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestTreatmentTaskTest {

    private List<Request> arrayWithOneRequest;
    private List<Request> pendingRequests;
    private RequestTreatmentTask requestTreatmentTask;
    @Mock
    private EvaluationStrategy assignerStrategyMock;
    @Mock
    private RoomRepository reservablesRepositoryMock;
    @Mock
    private SortingRequestStrategy requestSortingStrategyMock;
    @Mock
    private Request requestMock;
    @Mock
    private Room roomMock;
    @Mock
    private MailSender mailSenderMock;
    @Mock
    private PersonRepository personRepositoryMock;

    @Before
    public void initializeRequestTreatment() throws InterruptedException {
        arrayWithOneRequest = new ArrayList<>();
        arrayWithOneRequest.add(requestMock);
        pendingRequests = new ArrayList<>();
        requestTreatmentTask = new RequestTreatmentTask(assignerStrategyMock, requestSortingStrategyMock, reservablesRepositoryMock, pendingRequests, mailSenderMock, personRepositoryMock);
    }

    @Test
    public void givenPendingRequest_WhenRun_ThenShouldSortIt() {
        requestTreatmentTask.run();

        verify(requestSortingStrategyMock).sortList(pendingRequests);
    }

    private void havingOnePendingRequest() throws EvaluationNoRoomFoundException {
        when(assignerStrategyMock.evaluateOneRequest(reservablesRepositoryMock, requestMock)).thenReturn(roomMock);
        when(requestSortingStrategyMock.sortList(pendingRequests)).thenReturn(arrayWithOneRequest);
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

        verify(requestMock).reserve(roomMock);
    }

    @Test
    public void givenOnePendingRequest_WhenReserveSuccess_ThenShouldUpdateRepository() throws EvaluationNoRoomFoundException, RoomAlreadyReservedException, RoomInsufficientSeatsException, InvalidFormatException {
        havingOnePendingRequest();

        requestTreatmentTask.run();

        verify(reservablesRepositoryMock).persist(roomMock);
    }
}
