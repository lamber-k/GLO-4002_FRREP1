package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SequentialSortingRequestStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.marv1n.uat.fakes.RequestRepositoryInMemoryFake;
import ca.ulaval.glo4002.marv1n.uat.fakes.RoomRepositoryInMemoryFake;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignStepsState> {

    public static final String A_ROOM = "Une salle";
    public static final String ANOTHER_ROOM = "Une autre salle";
    private static final int ANOTHER_ROOM_SEATS_NUMBER = 12;
    public static final int ROOM_SEATS_NUMBER = 10;
    public static final int REQUEST_NUMBER_OF_SEATS_NEEDED = 5;
    public static final int REQUEST_PRIORITY = 5;
    public static final int MAXIMUM_PENDING_REQUESTS = 2;
    public static final int INTERVAL_TIMER = 10;
    public static final Person REQUEST_RESPONSIBLE = new Person();

    @Override
    protected AssignStepsState getInitialState() { return new AssignStepsState(); }

    @Given("an existing pending request")
    public void givenAnExistingPendingRequest() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().firstRoom = new Room(ROOM_SEATS_NUMBER, A_ROOM);
        state().secondRoom = new Room(ANOTHER_ROOM_SEATS_NUMBER, ANOTHER_ROOM);
        state().roomRepositoryInMemoryFake.persist(state().firstRoom);
        state().roomRepositoryInMemoryFake.persist(state().secondRoom);
        state().request = new Request(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
        state().pendingRequests.addRequest(state().request);
    }

    @When("I treat pending requests to the first available room")
    public void whenITreatPendingRequestsToTheFirstAvailableRoom() {
        state().sortingRequestStrategy = new SequentialSortingRequestStrategy();
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemoryFake, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemoryFake);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), INTERVAL_TIMER, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Then("the request should be assigned to the first available room")
    public void thenTheRequestShouldBeAssignedToTheFirstAvailableRoom() {
        assertEquals(state().firstRoom, state().request);
    }

    public class AssignStepsState extends StepState {
        public Room firstRoom;
        public Room secondRoom;
        public Request request;
        public PendingRequests pendingRequests;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public TaskScheduler taskScheduler;
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RoomRepository roomRepositoryInMemoryFake = new RoomRepositoryInMemoryFake();
        public RequestRepository requestRepositoryInMemoryFake = new RequestRepositoryInMemoryFake();
        public SortingRequestStrategy sortingRequestStrategy;
        public EvaluationStrategy evaluationStrategy;
    }
}
