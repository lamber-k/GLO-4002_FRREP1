package infrastructure.rest.configuration;

import core.PendingRequests;
import core.TaskSchedulerFactory;
import core.request.evaluation.EvaluationStrategy;
import core.request.evaluation.MaximizeSeatsEvaluationStrategy;
import core.request.sorting.SortingRequestByPriorityStrategy;
import core.request.sorting.SortingRequestStrategy;
import core.room.RoomRepository;
import infrastructure.persistence.RoomRepositoryHibernate;

import java.util.concurrent.TimeUnit;

public class StartupApplication {

    private int maximumPendingRequests = 50; // TODO MCS migrate file
    private int intervalTimer = 1;
    private TimeUnit timeUnit = TimeUnit.HOURS;
    private TaskSchedulerFactory schedulerFactory;
    private RoomRepository roomRepository;
    private EvaluationStrategy strategyAssignation;
    private SortingRequestStrategy sortingRequestStrategy;

    public PendingRequests pendingRequests;

    public void init() {
        this.startOrganizer();
    }

    private void startOrganizer() {
        roomRepository = new RoomRepositoryHibernate();
        strategyAssignation = new MaximizeSeatsEvaluationStrategy();
        sortingRequestStrategy = new SortingRequestByPriorityStrategy();
        schedulerFactory = new TaskSchedulerFactory(strategyAssignation, sortingRequestStrategy, roomRepository, intervalTimer, timeUnit);
        pendingRequests = new PendingRequests(maximumPendingRequests, schedulerFactory);
    }
}
