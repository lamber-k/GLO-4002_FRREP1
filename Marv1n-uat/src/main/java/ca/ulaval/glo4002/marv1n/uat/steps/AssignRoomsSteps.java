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
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignStepsState> {

    public static final String A_ROOM = "Une salle";
    public static final String ANOTHER_ROOM = "Une autre salle";
    public static final int ROOM_SEATS_NUMBER = 10;
    public static final int REQUEST_NUMBER_OF_SEATS_NEEDED = 5;
    public static final int REQUEST_PRIORITY = 5;
    public static final int MAXIMUM_PENDING_REQUESTS = 2;
    public static final int INTERVAL_TIMER = 10;
    public static final Person REQUEST_RESPONSIBLE = new Person();
    private static final int ANOTHER_ROOM_SEATS_NUMBER = 12;

    @Override
    protected AssignStepsState getInitialState() {
        return new AssignStepsState();
    }

    @Given("a new pending reservation")
    public void givenANewPendingReservation() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().firstRoom = new Room(ROOM_SEATS_NUMBER, A_ROOM);
        state().secondRoom = new Room(ANOTHER_ROOM_SEATS_NUMBER, ANOTHER_ROOM);
        state().roomRepositoryInMemory.persist(state().firstRoom);
        state().roomRepositoryInMemory.persist(state().secondRoom);
        state().request = new Request(REQUEST_NUMBER_OF_SEATS_NEEDED, REQUEST_PRIORITY, REQUEST_RESPONSIBLE);
        state().pendingRequests = new PendingRequests(MAXIMUM_PENDING_REQUESTS);
        state().pendingRequests.addRequest(state().request);
    }

    @When("I treat pending reservation to the first available room")
    public void whenITreatPendingRequestsToTheFirstAvailableRoom() {
        state().sortingRequestStrategy = new SequentialSortingRequestStrategy();
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), INTERVAL_TIMER, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Then("the reservation should be assigned to the first available room")
    public void thenTheRequestShouldBeAssignedToTheFirstAvailableRoom() {
        assertEquals(state().firstRoom, state().request.getReservedRoom());
    }

    private void addAPendingRequest() {
        state().request = new Request(5, 5, new Person());
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @Given("some room")
    public void givenSomeRoom() {
        state().firstRoom = new Room(5, "Une salle");
        state().roomRepositoryInMemory.persist(state().firstRoom);
        state().secondRoom = new Room(10, "Une autre salle");
        state().roomRepositoryInMemory.persist(state().secondRoom);
    }

    public class AssignStepsState extends StepState {
        public Room firstRoom;
        public Room secondRoom;
        public Request request;
        public PendingRequests pendingRequests;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public TaskScheduler taskScheduler;
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RoomRepository roomRepositoryInMemory = new RoomRepositoryInMemory();
        public RequestRepository requestRepositoryInMemory = new RequestRepositoryInMemory();
        public SortingRequestStrategy sortingRequestStrategy;
        public EvaluationStrategy evaluationStrategy;
    }
}
