package ca.ulaval.glo4002.core;

import ca.ulaval.glo4002.core.request.Request;
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

    //TODO read these from a config file
    private int defaultIntervalTimer;
    private TimeUnit defaultTimeUnit;

    //TODO could also read EvaluationStrategy and SortingStrategy from config within task factory

    public TaskSchedulerFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository) {
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.roomRepository = roomRepository;
    }

    public TaskSchedulerFactory(EvaluationStrategy strategyAssignation,
                                SortingRequestStrategy strategySortRequest,
                                RoomRepository roomRepository,
                                int intervalTimer,
                                TimeUnit timeUnit) {
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.roomRepository = roomRepository;
        this.defaultIntervalTimer = intervalTimer;
        this.defaultTimeUnit = timeUnit;
    }

    public Scheduler getTaskSheduler(List<Request> pendingRequests) {
        RequestTreatmentTaskFactory requestTreatementFactory = new RequestTreatmentTaskFactory(strategyAssignation, strategySortRequest, roomRepository, pendingRequests);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        return new TaskScheduler(scheduledExecutorService, defaultIntervalTimer, defaultTimeUnit, requestTreatementFactory);
    }
}
