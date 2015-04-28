package ca.ulaval.glo4002.marv1n.uat.steps.state;

import org.jbehave.core.annotations.BeforeScenario;

import java.util.HashMap;
import java.util.Map;

public class StatefulStep<T extends StepState> {

    private static ThreadLocal<Map<Class<?>, Object>> perThreadState = new ThreadLocal<>();

    static {
        perThreadState.set(new HashMap<>());
    }

    protected T getInitialState() {
        return null;
    }

    @BeforeScenario
    public void createState() {
        T initialState = getInitialState();
        if (initialState != null) {
            perThreadState.get().put(getClass(), initialState);
        }
    }

    @SuppressWarnings("unchecked")
    protected T state() {
        return (T) perThreadState.get().get(getClass());
    }
}
