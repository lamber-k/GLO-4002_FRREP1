package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.core.PendingRequests;
import ca.ulaval.glo4002.core.RequestTreatmentTaskFactory;
import ca.ulaval.glo4002.core.TaskScheduler;
import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestByPriorityStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.Room;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.locator.LocatorService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StartupApplication {

    private static String CONFIG_FILE_NAME = "configuration/application.properties";
    private int maximumPendingRequests;
    private int intervalTimer;
    private TimeUnit timeUnit = TimeUnit.HOURS;
    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy sortingRequestStrategy;

    private PendingRequests pendingRequests;
    private NotificationFactory notificationFactory;
    private RequestRepository requestRepository;
    private RequestTreatmentTaskFactory requestTreatmentTaskFactory;
    private TaskScheduler taskScheduler;

    public StartupApplication() throws IOException {
        roomRepository = LocatorService.getInstance().resolve(RoomRepository.class);
        notificationFactory = LocatorService.getInstance().resolve(NotificationFactory.class);
        requestRepository = LocatorService.getInstance().resolve(RequestRepository.class);
        loadConfiguration();
        strategyAssignation = new MaximizeSeatsEvaluationStrategy();
        sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        pendingRequests = new PendingRequests(maximumPendingRequests);
        requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignation, sortingRequestStrategy, roomRepository, pendingRequests, notificationFactory, requestRepository);
        taskScheduler = new TaskScheduler(Executors.newSingleThreadScheduledExecutor(), intervalTimer, timeUnit, requestTreatmentTaskFactory);
        pendingRequests.setScheduler(taskScheduler);
        taskScheduler.startScheduler();
        LocatorService.getInstance().register(PendingRequests.class, pendingRequests);
    }

    private void loadConfiguration() throws IOException {
        ApplicationConfiguration configuration = new ApplicationConfiguration(CONFIG_FILE_NAME);
        maximumPendingRequests = configuration.getMaximumPendingRequest();
        intervalTimer = configuration.getIntervalTimer();
        List<Room> defaultRooms = configuration.getDefaultRooms();
        defaultRooms.forEach(roomRepository::persist);
    }


    public PendingRequests getPendingRequests() {
        return pendingRequests;
    }
}
