package ca.ulaval.glo4002.core;

public interface TaskFactory {
    Task createTask(Task previousTask);
}
