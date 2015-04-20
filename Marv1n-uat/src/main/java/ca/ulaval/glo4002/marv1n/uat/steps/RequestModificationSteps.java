package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.*;
import ca.ulaval.glo4002.core.notification.Notification;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestModificationSteps extends StatefulStep<RequestModificationSteps.RequestStepsState> {

    @Override
    protected RequestStepsState getInitialState() {
        return new RequestStepsState();
    }

    @Given("an existing assigned reservation")
    public void givenAnExistingAssignedReservation() throws RoomAlreadyReservedException {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().room = new Room(10, "Une salle");
        state().roomRepositoryInMemoryFake.persist(state().room);
        state().request = new Request(5, 5, new Person());
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemoryFake, state().pendingRequests, state().notificationFactory, state().requestRepositoryInMemoryFake);
        state().taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), 10, TimeUnit.SECONDS, state().requestTreatmentTaskFactory);
        state().taskScheduler.run();
    }

    @Given("an existing pending reservation")
    public void givenAnExistingPendingReservation() {
        when(state().notificationFactory.createNotification(any(Request.class))).thenReturn(mock(Notification.class));
        state().request = new Request(5, 5, new Person(), null);
        state().pendingRequests = new PendingRequests(2);
        state().pendingRequests.addRequest(state().request);
    }

    @When("I cancel this reservation")
    public void whenICancelThisReservation() throws ObjectNotFoundException {
        state().requestCancellation = new RequestCancellation(state().pendingRequests, state().requestRepositoryInMemoryFake, state().notificationFactory);
        state().requestCancellation.cancelRequestByUUID(state().request.getRequestID());
    }

    @Then("the assigned reservation should have been cancelled")
    public void thenTheAssignedReservationShouldHaveBeenCancelled() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().requestRepositoryInMemoryFake.findByUUID(state().request.getRequestID()).getRequestStatus());
    }

    @Then("the pending reservation should have been cancelled")
    public void thenThePendingReservationShouldHaveBeenCancelled() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
    }

    @Then("the status of the assigned reservation should have changed")
    public void thenTheStatusOfTheAssignedReservationShouldHaveChanged() throws RequestNotFoundException {
        assertEquals(RequestStatus.CANCELED, state().requestRepositoryInMemoryFake.findByUUID(state().request.getRequestID()).getRequestStatus());
    }

    @Then("the room should have been unassigned")
    public void thenTheRoomShouldHaveBeenUnassigned() {
        assertEquals(null, state().room.getRequest());
    }

    @Then("the assigned reservation should have been archived")
    public void thenTheAssignedReservationShouldHaveBeenArchived() {
    }

    @Then("the pending reservation should have been archived")
    public void thenThePendingReservationShouldHaveBeenArchived() {
    }

    public class RequestStepsState extends StepState {
        public Room room;
        public Request request;
        public RequestTreatmentTaskFactory requestTreatmentTaskFactory;
        public EvaluationStrategy evaluationStrategy = new FirstInFirstOutEvaluationStrategy();
        public SortingRequestStrategy sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        public RoomRepositoryInMemoryFake roomRepositoryInMemoryFake = new RoomRepositoryInMemoryFake();
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RequestRepositoryInMemoryFake requestRepositoryInMemoryFake = new RequestRepositoryInMemoryFake();
        public RequestCancellation requestCancellation;
        public TaskScheduler taskScheduler;
        public Scheduler scheduler;
        public PendingRequests pendingRequests;
    }
}