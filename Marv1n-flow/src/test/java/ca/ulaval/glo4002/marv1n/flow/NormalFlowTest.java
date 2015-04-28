package ca.ulaval.glo4002.marv1n.flow;

import ca.ulaval.glo4002.core.ObjectNotFoundException;
import ca.ulaval.glo4002.core.RequestCancellation;
import ca.ulaval.glo4002.rest.StartupRest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class NormalFlowTest {


    private static final int DEFAULT_PORT = 8080;
    private StartupRest rest;

    @Before
    public void startApplication() throws IOException {
        rest = new StartupRest(DEFAULT_PORT);
        rest.start();
    }

    @Test
    public void normalFlow() throws ObjectNotFoundException {
        reserve();

        cancel();
    }

    private void reserve() {

    }

    private void cancel() throws ObjectNotFoundException {
        /*RequestCancellation requestCancellation = new RequestCancellation(pendingRequests, requestRepositoryInMemory, notificationFactory);
        requestCancellation.cancelRequestByUUID(request.getRequestID());*/
    }
}
