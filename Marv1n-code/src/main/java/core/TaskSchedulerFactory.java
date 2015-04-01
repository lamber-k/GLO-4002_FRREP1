package core;

import core.request.Request;
import core.request.evaluation.EvaluationStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.RoomRepository;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TaskSchedulerFactory {
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy strategySortRequest;
    private RoomRepository roomRepository;
    private int DEFAULT_INTERVAL_TIMER;
    private TimeUnit DEFAULT_TIME_UNIT;

    public TaskSchedulerFactory(EvaluationStrategy strategyAssignation, SortingRequestStrategy strategySortRequest, RoomRepository roomRepository) {
        this.strategyAssignation = strategyAssignation;
        this.strategySortRequest = strategySortRequest;
        this.roomRepository = roomRepository;
    }

    public Scheduler getTaskSheduler(List<Request> pendingRequests) {
        RequestTreatmentFactory requestTreatementFactory = new RequestTreatmentFactory(strategyAssignation, strategySortRequest, roomRepository, pendingRequests);
        //TODO create ScheduledExecutorService;
        ScheduledExecutorService scheduledExecutorService = null;
        return new TaskScheduler(scheduledExecutorService, DEFAULT_INTERVAL_TIMER, DEFAULT_TIME_UNIT, requestTreatementFactory);
    }
}
