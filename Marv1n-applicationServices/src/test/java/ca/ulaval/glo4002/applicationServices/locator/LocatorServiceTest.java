package ca.ulaval.glo4002.applicationServices.locator;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LocatorServiceTest {

    private LocatorService locatorService = LocatorService.getInstance();
    private AService service = new AService();
    private ASecondService service2 = new ASecondService();

    @After
    public void clearLocatorService() {
        locatorService.unregisterAll();
    }

    @Test
    public void givenANewLocatorService_WhenGetInstance_ThenShouldReturnNewLocatorService() {
        assertEquals(LocatorService.class, locatorService.getClass());
    }

    @Test
    public void givenAnExistingInstanceOFLocatorService_WhenGetInstance_ThenShouldReturnTheCurrentInstanceOfLocatorService() {
        assertEquals(locatorService, LocatorService.getInstance());
    }

    @Test
    public void givenALocatorService_WhenRegisteringANewService_ThenShouldBeAbleToRetrieveServiceWithResolve() {
        locatorService.register(AService.class, service);

        assertEquals(service, locatorService.resolve(AService.class));
    }

    @Test
    public void givenALocatorService_WhenRegisteringASecondService_ThenShouldBeAbleToRetrieveAnyServiceWithResolve() {
        locatorService.register(AService.class, service);
        locatorService.register(ASecondService.class, service2);

        assertEquals(service2, locatorService.resolve(ASecondService.class));
        assertEquals(service, locatorService.resolve(AService.class));
    }

    @Test
    public void givenALocatorServiceContainingAService_WhenUnregisterAll_ThenShouldNotContainTheServiceAnymore() {
        locatorService.register(AService.class, service);

        locatorService.unregisterAll();

        assertTrue(locatorServiceDoesNotContainAServiceService());
    }

    public boolean locatorServiceDoesNotContainAServiceService() {
        try {
            locatorService.resolve(AService.class);
        } catch (UnregisteredServiceException e) {
            return true;
        }
        return false;
    }

    @Test(expected = MultipleRegistrationException.class)
    public void givenALocatorService_WhenRegisteringAnAlreadyPresentService_ThenShouldThrowMultipleRegistrationException() {
        locatorService.register(AService.class, service);
        locatorService.register(AService.class, service);
    }

    @Test(expected = UnregisteredServiceException.class)
    public void givenALocatorService_WhenAttemptingToRetrieveAnInexistingService_ThenShouldThrowMultipleRegistrationException() {
        locatorService.resolve(UnregistredService.class);
    }

    @Test
    public void givenALocatorService_WhenRegisterModuleContainingAService_ThenLocatorModuleLoadShouldBeCalled() {
        LocatorModule locatorModule = mock(LocatorModule.class);

        locatorService.registerModule(locatorModule);

        verify(locatorModule).load(any(LocatorContainer.class));
    }

    @Test
    public void givenALocatorService_WhenRegisterModuleContainingAService_ThenLocatorServiceShouldContainAService() {
        LocatorModule locatorModuleContainingAService = new LocatorModuleMock();

        locatorService.registerModule(locatorModuleContainingAService);

        assertEquals(service, locatorService.resolve(AService.class));
    }

    @Test
    public void givenALocatorServiceContainingService_WhenRegisterModuleContainingService_ThenLocatorServiceShouldMergeListOfService() {
        locatorService.register(ASecondService.class, service2);
        LocatorModule locatorModuleContainingAService = new LocatorModuleMock();

        locatorService.registerModule(locatorModuleContainingAService);

        assertEquals(service, locatorService.resolve(AService.class));
        assertEquals(service2, locatorService.resolve(ASecondService.class));
    }

    public class AService {
    }

    public class ASecondService {
    }

    public class UnregistredService {
    }

    public class LocatorModuleMock implements LocatorModule {

        @Override
        public void load(LocatorContainer container) {
            container.register(AService.class, service);
        }
    }
}
