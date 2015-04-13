package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.locator.LocatorService;
import ca.ulaval.glo4002.persistence.hibernate.RoomRepositoryHibernate;
import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.TaskSchedulerFactory;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StartupApplication {

    private int maximumPendingRequests = 50; // TODO MCS migrate file
    private int intervalTimer = 1;
    private TimeUnit timeUnit = TimeUnit.HOURS;
    private TaskSchedulerFactory schedulerFactory;
    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy sortingRequestStrategy;

    private PendingRequests pendingRequests;
    private NotificationFactory notificationFactory;
    private RequestRepository requestRepository;
    private RequestTreatmentTaskFactory requestTreatmentTaskFactory;
    private TaskScheduler taskScheduler;

    public void init() {
        this.startOrganizer();
    }

    private void startOrganizer() {
        roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
        notificationFactory = LocatorService.getInstance().resolve(NotificationFactory.class);
        requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        strategyAssignation = new MaximizeSeatsEvaluationStrategy();
        sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        pendingRequests = new PendingRequests(maximumPendingRequests);
        requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignation, sortingRequestStrategy, roomRepository, pendingRequests, notificationFactory, requestRepository);
        schedulerFactory = new TaskSchedulerFactory(strategyAssignation, sortingRequestStrategy, roomRepository, notificationFactory, requestRepository, intervalTimer, timeUnit);
        taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), intervalTimer, timeUnit, requestTreatmentTaskFactory);
        pendingRequests.setScheduler(taskScheduler);
    }

    public PendingRequests getPendingRequests() {
        return pendingRequests;
    }
}
