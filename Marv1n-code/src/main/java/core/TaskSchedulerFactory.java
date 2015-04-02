package core;

import core.request.Request;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.RoomRepository;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TaskSchedulerFactory {
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private RoomRepository roomRepository;

    //TODO read these from a config file
    private int DEFAULT_INTERVAL_TIMER;
    private TimeUnit DEFAULT_TIME_UNIT;

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
        this.DEFAULT_INTERVAL_TIMER = intervalTimer;
        this.DEFAULT_TIME_UNIT = timeUnit;
    }

    public Scheduler getTaskSheduler(List<Request> pendingRequests) {
        RequestTreatmentTaskFactory requestTreatementFactory = new RequestTreatmentTaskFactory(strategyAssignation, strategySortRequest, roomRepository, pendingRequests);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        return new TaskScheduler(scheduledExecutorService, DEFAULT_INTERVAL_TIMER, DEFAULT_TIME_UNIT, requestTreatementFactory);
    }
}
