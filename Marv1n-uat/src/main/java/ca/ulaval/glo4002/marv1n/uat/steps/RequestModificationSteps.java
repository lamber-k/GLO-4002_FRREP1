package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.*;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.persistence.InvalidFormatException;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestNotFoundException;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.FirstInFirstOutEvaluationStrategy;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RequestModificationSteps extends StatefulStep<RequestModificationSteps.RequestStepsState> {

    @Override
    protected RequestStepsState getInitialState() {
        return new RequestStepsState();
    }

    @Given("an existing assigned reservation")
    public void givenAnExistingAssignedReservation() throws RoomAlreadyReservedException {
        state().room = new Room(5, "Une salle");
        state().request = new Request(5, 5, new Person());
        state().requestsToTreat.add(state().request);
        state().requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemoryFake, state().requestsToTreat, state().notificationFactory, state().requestRepositoryInMemoryFake);
        state().taskSchedulerFactory = new TaskSchedulerFactory(state().evaluationStrategy, state().sortingRequestStrategy, state().roomRepositoryInMemoryFake, state().notificationFactory, state().requestRepositoryInMemoryFake);
        state().scheduler = state().taskSchedulerFactory.getTaskScheduler(state().requestsToTreat);
        state().scheduler.runNow();
    }

    @Given("an existing pending reservation")
    public void givenAnExistingPendingReservation() {
        state().request = new Request(5, 5, new Person(), null);
        state().requestsToTreat.add(state().request);
    }

    @When("I cancel this reservation")
    public void whenICancelThisReservation() throws InvalidFormatException {
        state().pendingRequests = new PendingRequests(2, state().taskSchedulerFactory);
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
        public SortingRequestStrategy sortingRequestStrategy = mock(SortingRequestStrategy.class);
        public RoomRepositoryInMemoryFake roomRepositoryInMemoryFake = new RoomRepositoryInMemoryFake();
        public List<Request> requestsToTreat = new ArrayList<Request>();
        public NotificationFactory notificationFactory = mock(NotificationFactory.class);
        public RequestRepositoryInMemoryFake requestRepositoryInMemoryFake = new RequestRepositoryInMemoryFake();
        public RequestCancellation requestCancellation;
        public TaskSchedulerFactory taskSchedulerFactory;
        public Scheduler scheduler;
        public PendingRequests pendingRequests;
    }
}
