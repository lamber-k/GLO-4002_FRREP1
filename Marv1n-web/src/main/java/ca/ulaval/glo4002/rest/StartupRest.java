package ca.ulaval.glo4002.rest;

import ca.ulaval.glo4002.applicationServices.locator.LocatorService;
import ca.ulaval.glo4002.rest.configuration.EntityManagerContextFilter;
import ca.ulaval.glo4002.rest.configuration.LocatorServicesModule;
import ca.ulaval.glo4002.rest.configuration.StartupApplication;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

public class StartupRest {

    private int httpPort;

    public StartupRest(int httpPort) {
        this.httpPort = httpPort;
    }

    public void start() {
        try {
            configureApplication();
        } catch (IOException exception) {
            System.err.print(exception.getMessage());
            return;
        }
        startRest();
    }

    private void startRest() {
        try {
            Server server = new Server(this.httpPort);
            ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
            configureJersey(servletContextHandler);
            server.start();
            server.join();
        } catch (Exception exception) {
            throw new RestServerLaunchFailException(exception);
        }
    }

    private void configureApplication() throws IOException {
        LocatorService.getInstance().registerModule(new LocatorServicesModule());
        new StartupApplication();
    }

    private void configureJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages(
                "ca.ulaval.glo4002.rest.resources").register(JacksonFeature.class));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
