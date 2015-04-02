package ca.ulaval.glo4002.Marv1n.applicationServices.locator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocatorContainerTest {

    private LocatorContainer locatorContainer;
    private ImplementationTestSampleService implementationTestSampleService;
    private OtherTestSampleService AnOtherObjectForRegistering;

    @Before
    public void setup() {
        locatorContainer = new LocatorContainer();
        implementationTestSampleService = new ImplementationTestSampleService();
    }

    @Test(expected = UnregisteredServiceException.class)
    public void GivenNewInstance_WhenResolve_ThenShouldThrowException() {
        locatorContainer.resolve(ImplementationTestSampleService.class);
    }

    @Test
    public void GivenInstanceWithRegisteredService_WhenResolve_ThenShouldReturnInstanceRegistered() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);

        TestSampleService service = locatorContainer.resolve(TestSampleService.class);

        Assert.assertEquals(implementationTestSampleService, service);
    }

    @Test(expected = MultipleRegistrationException.class)
    public void GivenInstanceWithAlreadyRegisteredService_WhenRegisterTheSameService_ThenShouldThrowException() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);

        locatorContainer.register(TestSampleService.class, implementationTestSampleService);
    }

    @Test
    public void GiveTwoInstanceWithRegisteredServices_WhenMergeAnInstanceWithTheOther_ThenTheInstanceMergedShouldHaveRegisteredObjectOfSecondInstance() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);
        LocatorContainer otherContainer = new LocatorContainer();
        AnOtherObjectForRegistering = new ImplementationOtherTestSampleService();
        otherContainer.register(OtherTestSampleService.class, AnOtherObjectForRegistering);

        locatorContainer.merge(otherContainer);

        assertEquals(AnOtherObjectForRegistering, locatorContainer.resolve(OtherTestSampleService.class));
    }

    private interface TestSampleService {

    }

    private class ImplementationTestSampleService implements TestSampleService {

    }

    private interface OtherTestSampleService {

    }

    private class ImplementationOtherTestSampleService implements OtherTestSampleService {

    }
}
