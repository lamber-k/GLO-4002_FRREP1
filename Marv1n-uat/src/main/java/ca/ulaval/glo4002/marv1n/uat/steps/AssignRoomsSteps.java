package ca.ulaval.glo4002.marv1n.uat.steps;

import ca.ulaval.glo4002.marv1n.uat.steps.state.StatefulStep;
import ca.ulaval.glo4002.marv1n.uat.steps.state.StepState;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.fail;

public class AssignRoomsSteps extends StatefulStep<AssignRoomsSteps.AssignStepsState> {

    @Override
    protected AssignStepsState getInitialState() { return new AssignStepsState(); }

    @Given("a pending request")
    public void givenAPendingRequest() {
        fail();
    }

    @When("I treat pending requests to the first available room")
    public void whenITreatPendingRequestsToTheFirstAvailableRoom() {

    }

    @Then("the request should be assigned to the first available room")
    public void thenTheRequestShouldBeAssignedToTheFirstAvailableRoom() {

    }

    public class AssignStepsState extends StepState {

    }
}
