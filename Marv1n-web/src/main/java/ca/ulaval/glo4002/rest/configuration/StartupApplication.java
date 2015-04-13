package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.locator.LocatorService;
import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.TaskSchedulerFactory;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;

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

    public StartupApplication() {
        roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
        notificationFactory = LocatorService.getInstance().resolve(NotificationFactory.class);
        requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        strategyAssignation = new MaximizeSeatsEvaluationStrategy();
        sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        schedulerFactory = new TaskSchedulerFactory(strategyAssignation, sortingRequestStrategy, roomRepository, notificationFactory, requestRepository, intervalTimer, timeUnit);
        setPendingRequests(new PendingRequests(maximumPendingRequests, schedulerFactory));
    }

    public PendingRequests getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(PendingRequests pendingRequests) {
        this.pendingRequests = pendingRequests;
    }
}
