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
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SequentialSortingRequestStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.InOrder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignStepsState> {

    private static final int TWO_SECOND_IN_MILLIS = 2 * 1000;

    @Override
    protected AssignStepsState getInitialState() {
        return new AssignStepsState();
    }

    @Given("a pendingRequest system")
    public void givenAPendingRequestSystem(){
        state().pendingRequests = new PendingRequests(5);
    }

    @Given("a new reservation")
    public void givenANewReservation() {
        addAPendingRequest();
    }

    private void addAPendingRequest() {
        state().request = spy(new Request(5, 5, new Person()));
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @Given("a maximize seats evaluation strategy")
    public void givenAMaximizeSeatsEvaluationStrategy() {
        state().evaluationStrategy = new MaximizeSeatsEvaluationStrategy();
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
    }

    @Given("a first assigned room with lower capacity")
    public void givenAFirstAssignedRoomWithLowerCapacity() throws RoomAlreadyReservedException {
        state().firstRoom = new Room(5, "Premiere salle");
        state().firstRoom.book(mock(Request.class));
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @Given("a second unassigned room with medium capacity")
    public void givenASecondUnassignedRoomWithMediumCapacity() {
        state().secondRoom = new Room(7, "Seconde salle");
        state().roomRepositoryInMemory.persist(state().secondRoom);
    }

    @Given("a third unassigned room with higher capacity")
    public void givenAThirdUnassignedRoomWithHigherCapacity() {
        state().thirdRoom = new Room(10, "Troisieme salle");
        state().roomRepositoryInMemory.persist(state().thirdRoom);
    }

    @Given("a first unassigned room with medium capacity")
    public void givenAFirstUnassignedRoomWithMediumCapacity() {
        state().firstRoom = new Room(7, "premiere salle");
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @When("I treat with the maximize seats evaluation strategy")
    public void whenITreatWithTheMaximizeSeatsEvaluationStrategy() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Then("the unassigned room with minimum seats, but enough, should have been assigned")
    public void thenTheUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
        assertTrue(state().secondRoom.isReserved());
    }

    @Then("an unassigned room with minimum seats, but enough, should have been assigned")
    public void thenAnUnassignedRoomWithMinimumSeatsButEnoughShouldHaveBeenAssigned() {
        assertTrue(state().firstRoom.isReserved() ^ state().secondRoom.isReserved());
    }

    @Given("multiple pending reservation with different priority")
    public void givenMultiplePendingReservationWithDifferentPriority() {
        state().request = spy(new Request(5, 1,new Person()));
        state().secondRequest = spy(new Request(5, 3,new Person()));
        state().thirdRequest = spy(new Request(5, 5,new Person()));
        state().pendingRequests.addRequest(state().request);
        state().pendingRequests.addRequest(state().secondRequest);
        state().pendingRequests.addRequest(state().thirdRequest);
    }

    @Given("multiple avalible room fiting request")
    public void givenMultipleAvalibleRoomFitingRequests(){
        state().firstRoom = spy(new Room(5,"a name"));
        state().secondRoom = spy(new Room(5,"a second name"));
        state().thirdRoom = spy(new Room(5,"a third name"));
        state().roomRepositoryInMemory.persist(state().firstRoom);
        state().roomRepositoryInMemory.persist(state().secondRoom);
        state().roomRepositoryInMemory.persist(state().thirdRoom);
    }


    @Then("pending reservation are being treated in order of priority")
    public void thenPendingReservationAreBeingTreatedInOrderOfPriority() {
        InOrder inOrder = inOrder(state().request,state().secondRequest,state().thirdRequest);

        inOrder.verify(state().request).reserve(any(Room.class));
        inOrder.verify(state().secondRequest).reserve(any(Room.class));
        inOrder.verify(state().thirdRequest).reserve(any(Room.class));
    }

    @Given("evaluation strategy assign to first available room")
    public void givenPendingReservationAssignedToTheFirstAvailableRoom() {
        state().evaluationStrategy = spy(new FirstInFirstOutEvaluationStrategy());
    }

    @When("I treat pending reservation")
    public void whenITreatPendingReservation() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemory, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemory);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Then("the reservation should be assigned to the first available room")
    public void thenTheReservationShouldBeAssignedToTheFirstAvailableRoom() {
        verify(state().request).reserve(state().firstRoom);
    }

    @Given("a request treatment with a scheduler")
    public void givenARequestTreatmentWithAScheduler() {
        state().taskScheduler = spy(new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 1000, TimeUnit.SECONDS, state().requestTreatmentTaskFactory));
    }

    @When("I start the scheduler to call the request treatment periodicaly")
    public void whenIStartTheSchedulerToCallTheRequestTreatmentPeriodicaly() {
        state().taskScheduler.setIntervalTimer(2);
        state().taskScheduler.startScheduler();
    }

    @Then("pending reservations are being treated periodicaly")
    public void thenPendingReservationsAreBeingTreatedPeriodically() {
        verify(state().taskScheduler,timeout(10*1000).atLeast(3)).run();
    }

    @Given("a new reservation with medium priority")
    public void givenANewReservationWithMediumPriority() {
        state().request = spy(new Request(3,3,new Person()));
        state().pendingRequests.addRequest(state().request);
    }

    @Given("a second reservation with medium priority")
    public void givenSecondReservationWithMediumPriority() {
        state().secondRequest = spy(new Request(3,3,new Person()));
        state().pendingRequests.addRequest(state().secondRequest);
    }

    @Given("a room fiting medium priority request")
    public void givenARoomFitingMediumPriorityRequest() {
        state().firstRoom = spy(new Room(3, "a name"));
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @Given("a second room fiting medium priority request")
    public void givenASecondRoomFitingMediumPriorityRequest() {
        state().secondRoom = spy(new Room(3, "a second name"));
        state().roomRepositoryInMemory.persist(state().secondRoom);
    }

    @Given("a room")
    public void givenARoom() {
        state().firstRoom = spy(new Room(5, "a name"));
        state().roomRepositoryInMemory.persist(state().firstRoom);
    }

    @Given("a second room")
    public void givenASecondRoom() {
        state().secondRoom = spy(new Room(5, "a second name"));
        state().roomRepositoryInMemory.persist(state().secondRoom);
    }

    @Given("an evaluation strategy")
    public void givenAnEvaluationStrategy() {
        state().evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
    }

    @Given("a sorting request strategy")
    public void givenASortingRequestStrategy() {
        givenASortingRequestByArrivalOrderStrategy();
    }

    @Given("a sorting request by priority strategy")
    public void givenASortingRequestByPriorityStrategy() {
        state().sortingRequestStrategy = spy(new SortingRequestByPriorityStrategy());
    }

    @Given("a sorting request by arrival order strategy")
    public void givenASortingRequestByArrivalOrderStrategy() {
        state().sortingRequestStrategy = spy(new SequentialSortingRequestStrategy());
    }

    @Then("same priority demads are treat in order of arrival")
    public void thenSamePriorityDemadsAreTreatInOrderOfArrival() {
        InOrder inOrder = inOrder(state().request, state().secondRequest);

        inOrder.verify(state().request).reserve(any(Room.class));
        inOrder.verify(state().secondRequest).reserve(any(Room.class));
    }

    @Given("a limit of pending request")
    public void givenALimitOfPendingRequest() {
        state().pendingRequests.setMaximumPendingRequests(2);
    }

    @When("the limit of pending reservation is reached")
    public void whenTheLimitOfPendingReservationIsReached(){
        state().pendingRequests.setScheduler(state().taskScheduler);
        givenSecondReservationWithMediumPriority();
    }

    @Then("the pending reservation are being immediately treated")
    public void thenThePendingReservationAreBeingImmediatelyTreated() {
        verify(state().taskScheduler, timeout(TWO_SECOND_IN_MILLIS).atLeastOnce()).runNow();
        verify(state().taskScheduler, timeout(TWO_SECOND_IN_MILLIS).atLeastOnce()).run();
    }

    @Then("the scheduler restart the timer")
    public void thenTheSchedulerRestartTheTimer() {
        verify(state().taskScheduler,timeout(TWO_SECOND_IN_MILLIS).atLeastOnce()).cancelScheduler();
    }

    public class AssignStepsState extends StepState {
        public Room firstRoom;
        public Room secondRoom;
        public Room thirdRoom;
        public Request request;
        public Request secondRequest;
        public Request thirdRequest;
        public PendingRequests pendingRequests;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public TaskScheduler taskScheduler;
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RoomRepository roomRepositoryInMemory = new RoomRepositoryInMemory();
        public RequestRepository requestRepositoryInMemory = new RequestRepositoryInMemory();
        public SortingRequestStrategy sortingRequestStrategy;
        public EvaluationStrategy evaluationStrategy;

        public AssignStepsState() {

        }
    }
}
