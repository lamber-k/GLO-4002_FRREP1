package ca.ulaval.glo4002.marv1n.flow;

import ca.ulaval.glo4002.core.*;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.notification.mail.MailNotificationFactory;
import ca.ulaval.glo4002.core.person.Person;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.persistence.inmemory.RequestRepositoryInMemory;
import ca.ulaval.glo4002.persistence.inmemory.RoomRepositoryInMemory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NormalFlowTest {

    private PendingRequests pendingRequests;
    private RequestRepository requestRepositoryInMemory;
    private NotificationFactory notificationFactory;
    private Request request;
    private Room room;
    private RoomRepository roomRepositoryInMemory;
    private RoomRepository roomRepository;

    @Before
    public void startApplication() {
/*        roomRepository = new RoomRepositoryInMemory();
        notificationFactory = new MailNotificationFactory(new );
        requestRepository = new RequestRepositoryInMemory();
        strategyAssignation = new MaximizeSeatsEvaluationStrategy();
        sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        pendingRequests = new PendingRequests(maximumPendingRequests);
        requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignation, sortingRequestStrategy, roomRepository, pendingRequests, notificationFactory, requestRepository);
        taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), intervalTimer, timeUnit, requestTreatmentTaskFactory);
        pendingRequests.setScheduler(taskScheduler);
        taskScheduler.startScheduler();

        request = new Request(5, 5, new Person());
        pendingRequests = new PendingRequests(2);
        pendingRequests.addRequest(request);
        room = new Room(5, "Une salle");
        roomRepositoryInMemory.persist(room);*/
    }

    @Test
    public void normalFlow() throws ObjectNotFoundException {
        reserve();

        cancel();
    }

    private void reserve() {

    }

    private void cancel() throws ObjectNotFoundException {
        RequestCancellation requestCancellation = new RequestCancellation(pendingRequests, requestRepositoryInMemory, notificationFactory);
        requestCancellation.cancelRequestByUUID(request.getRequestID());
    }
}
