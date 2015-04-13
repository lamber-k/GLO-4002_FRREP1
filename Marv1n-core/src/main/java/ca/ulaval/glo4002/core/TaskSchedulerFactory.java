package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.notification.NotificationFactory;
import ca.ulaval.glo4002.core.request.Request;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.request.evaluation.EvaluationStrategy;
import ca.ulaval.glo4002.core.request.sorting.SortingRequestStrategy;
import ca.ulaval.glo4002.core.room.RoomRepository;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TaskSchedulerFactory {
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private RoomRepository roomRepository;

    private NotificationFactory notificationFactory;
    private RequestRepository requestRepository;
    //TODO read these from a config file
    private int defaultIntervalTimer;
    private TimeUnit defaultTimeUnit;

    //TODO could also read EvaluationStrategy and SortingStrategy from config within task factory

    public TaskSchedulerFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository, NotificationFactory notificationFactory, RequestRepository requestRepository) {
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.roomRepository = roomRepository;
        this.notificationFactory = notificationFactory;
        this.requestRepository = requestRepository;
    }

    public TaskSchedulerFactory(EvaluationStrategy strategyAssignation,
                                SortingRequestStrategy strategySortRequest,
                                RoomRepository roomRepository,
                                NotificationFactory notificationFactory,
                                RequestRepository requestRepository,
                                int intervalTimer,
                                TimeUnit timeUnit) {
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.roomRepository = roomRepository;
        this.notificationFactory = notificationFactory;
        this.requestRepository = requestRepository;
        this.defaultIntervalTimer = intervalTimer;
        this.defaultTimeUnit = timeUnit;
    }

    public Scheduler getTaskScheduler(List<Request> pendingRequests) {
        //TODO ALL Test me properly
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        RequestTreatmentTaskFactory requestTreatmentTaskFactory = new RequestTreatmentTaskFactory(strategyAssignation, strategySortRequest, roomRepository, pendingRequests, notificationFactory, requestRepository);
        return new TaskScheduler(scheduledExecutorService, defaultIntervalTimer, defaultTimeUnit, requestTreatmentTaskFactory);
    }
}
