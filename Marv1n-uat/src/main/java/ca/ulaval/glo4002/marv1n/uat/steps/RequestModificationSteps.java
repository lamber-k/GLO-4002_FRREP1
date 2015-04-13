package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestStatus;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomAlreadyReservedException;
import ca.ulaval.glo4002.marv1n.uat.fakes.RequestRepositoryInMemoryFake;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class RequestModificationSteps extends StatefulStep<RequestModificationSteps.RequestStepsState> {

    @Override
    protected RequestStepsState getInitialState() {
        return new RequestStepsState();
    }

    @Given("an existing assigned reservation")
    public void givenAnExistingAssignedReservation() throws RoomAlreadyReservedException {
        state().room = new Room(5, "Une salle");
        state().request = new Request(5, 5, new Person(), null);
        state().request.reserve(state().room);
    }

    @Given("an existing pending reservation")
    public void givenAnExistingPendingReservation() {
        state().request = new Request(5, 5, new Person(), null);
    }

    @When("I cancel this reservation")
    public void whenICancelThisReservation() {
        state().request.cancel();
    }

    @Then("the reservation should have been cancelled")
    public void thenTheReservationShouldHaveBeenCancelled() {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
    }

    @Then("the room should have been unassigned")
    public void thenTheRoomShouldHaveBeenUnassigned() {
        assertEquals(null, state().room.getRequest());
    }

    @Then("the status of the reservation should have changed")
    public void thenTheStatusOfTheReservationShouldHaveChanged() {
        assertEquals(RequestStatus.CANCELED, state().request.getRequestStatus());
    }

    public class RequestStepsState extends StepState {
        public Room room;
        public Request request;
        // TODO : Une request n'est pas persist pour le moment
        public RequestRepositoryInMemoryFake requestRepositoryInMemoryFake;
    }
}
