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
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignRoomsStepsState> {

    //TODO Extract variable global

    @Override
    protected AssignRoomsStepsState getInitialState() {
        return new AssignRoomsStepsState();
    }

    @Given("a new reservation")
    public void givenANewReservation() {
        addAPendingRequest();
    }

    private void addAPendingRequest() {
        state().request = new Request(5, 5, new Person());
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
    @Pending
    public void givenMultiplePendingReservationWithDifferentPriority() {
        fail("Not implemented yet");
    }

    @Given("pending reservation treated by priority")
    @Pending
    public void givenPendingReservationTreatedByPriority() {
        fail("Not implemented yet");
    }

    @Then("pending reservation are being treated in order of priority")
    @Pending
    public void thenPendingReservationAreBeingTreatedInOrderOfPriority() {
        fail("Not implemented yet");
    }

    @Given("pending reservation assigned to the first available room")
    @Pending
    public void givenPendingReservationAssignedToTheFirstAvailableRoom() {
        fail("Not implemented yet");
    }

    @When("I treat pending reservation")
    @Pending
    public void whenITreatPendingReservation() {
        fail("Not implemented yet");
    }

    @Then("the reservation should be assigned to the first available room")
    @Pending
    public void thenTheReservationShouldBeAssignedToTheFirstAvailableRoom() {
        fail("Not implemented yet");
    }

    @Given("a request treatment with a scheduler")
    @Pending
    public void givenARequestTreatmentWithAScheduler() {
        fail("Not implemented yet");
    }

    @When("I start the scheduler to call the request treatment every 1 minutes")
    @Pending
    public void whenIStartTheSchedulerToCallTheRequestTreatmentEvery1Minutes() {
        fail("Not implemented yet");
    }

    @Then("pending reservations are being treated periodically")
    @Pending
    public void thenPendingReservationsAreBeingTreatedPeriodically() {
        fail("Not implemented yet");
    }

    @Given("a new reservation with medium priority")
    @Pending
    public void givenANewReservationWithMediumPriority() {
        fail("Not implemented yet");
    }

    @Given("another reservation with medium priority")
    @Pending
    public void givenAnotherReservationWithMediumPriority() {
        fail("Not implemented yet");
    }

    @Given("a sorting request by priority strategy")
    @Pending
    public void givenASortingRequestByPriorityStrategy() {
        fail("Not implemented yet");
    }

    @Then("same priority demads are treat in order of arrival")
    @Pending
    public void thenSamePriorityDemadsAreTreatInOrderOfArrival() {
        fail("Not implemented yet");
    }

    @Given("multiple pending reservation")
    @Pending
    public void givenMultiplePendingReservation() {
        fail("Not implemented yet");
    }

    @When("the limit of pending reservation is reached")
    @Pending
    public void whenTheLimitOfPendingReservationIsReached() {
        fail("Not implemented yet");
    }

    @Then("the pending reservation are being immediately treated")
    @Pending
    public void thenThePendingReservationAreBeingImmediatelyTreated() {
        fail("Not implemented yet");
    }

    @Then("the scheduler restart the timer")
    @Pending
    public void thenTheSchedulerRestartTheTimer() {
        fail("Not implemented yet");
    }

    public class AssignRoomsStepsState extends StepState {
        public Room firstRoom;
        public Room secondRoom;
        public Room thirdRoom;
        public Request request;
        public PendingRequests pendingRequests;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public TaskScheduler taskScheduler;
        public NotificationFactory notificationFactory;
        public RoomRepository roomRepositoryInMemory;
        public RequestRepository requestRepositoryInMemory;
        public SortingRequestStrategy sortingRequestStrategy;
        public EvaluationStrategy evaluationStrategy;

        public AssignRoomsStepsState() {
            this.notificationFactory = mock(NotificationFactory.class);
            this.roomRepositoryInMemory = new RoomRepositoryInMemory();
            this.requestRepositoryInMemory = new RequestRepositoryInMemory();
        }
    }
}
