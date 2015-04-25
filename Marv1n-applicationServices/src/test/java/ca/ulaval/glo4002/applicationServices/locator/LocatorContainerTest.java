package ca.ulaval.glo4002.applicationServices.locator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void givenNewInstance_WhenResolve_ThenShouldThrowException() {
        locatorContainer.resolve(ImplementationTestSampleService.class);
    }

    @Test
    public void givenInstanceWithRegisteredService_WhenResolve_ThenShouldReturnInstanceRegistered() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);

        TestSampleService service = locatorContainer.resolve(TestSampleService.class);

        Assert.assertEquals(implementationTestSampleService, service);
    }

    @Test(expected = MultipleRegistrationException.class)
    public void givenInstanceWithAlreadyRegisteredService_WhenRegisterTheSameService_ThenShouldThrowException() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);

        locatorContainer.register(TestSampleService.class, implementationTestSampleService);
    }

    @Test
    public void givenInstanceWithAlreadyRegisteredService_WhenClear_ThenRegistredServicesShouldBeRemoved() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);

        locatorContainer.clear();

        assertTrue(LocatorContainerDosentContainTestSampleService());
    }

    private boolean LocatorContainerDosentContainTestSampleService() {
        try {
            locatorContainer.resolve(TestSampleService.class);
        } catch (UnregisteredServiceException e) {
            return true;
        }
        return false;
    }

    @Test
    public void givenTwoInstanceWithRegisteredServices_WhenMergeAnInstanceWithTheOther_ThenTheInstanceMergedShouldHaveRegisteredObjectOfSecondInstance() {
        locatorContainer.register(TestSampleService.class, implementationTestSampleService);
        LocatorContainer otherContainer = new LocatorContainer();
        AnOtherObjectForRegistering = new ImplementationOtherTestSampleService();
        otherContainer.register(OtherTestSampleService.class, AnOtherObjectForRegistering);

        locatorContainer.merge(otherContainer);

        assertEquals(AnOtherObjectForRegistering, locatorContainer.resolve(OtherTestSampleService.class));
    }

    private interface TestSampleService {

    }

    private interface OtherTestSampleService {

    }

    private class ImplementationTestSampleService implements TestSampleService {

    }

    private class ImplementationOtherTestSampleService implements OtherTestSampleService {

    }
}
