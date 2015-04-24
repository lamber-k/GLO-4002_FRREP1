package ca.ulaval.glo4002.marv1n.uat;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ScanningStepsFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;

public class Marv1nStories extends JUnitStories {

    private PendingStepStrategy pendingStepStrategy = new FailingUponPendingStep();

    private Format[] formats = new Format[]{CONSOLE};
    private StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
            .withCodeLocation(codeLocationFromClass(Marv1nStories.class)).withFailureTrace(true).withFailureTraceCompression(true)
            .withDefaultFormats().withFormats(formats);
    private Embedder embedder = new Embedder();

    public Marv1nStories() {
        useEmbedder(embedder);
    }

    public static void main(String[] args) {
        new Marv1nStories().embedder.runAsEmbeddables(Arrays.asList(Marv1nStories.class.getCanonicalName()));
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .usePendingStepStrategy(pendingStepStrategy)
                .useStoryLoader(new LoadFromClasspath(getClass().getClassLoader()))
                .useStoryReporterBuilder(reporterBuilder);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        // Le ScanningStepsFactory implique qu'on doit ajouter une dépendence vers org.reflections. Voir le POM.
        return new ScanningStepsFactory(configuration(), getClass());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story", "*.story"), null);
    }
}