package ca.ulaval.glo4002.rest;

import ca.ulaval.glo4002.locator.LocatorService;
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
import java.util.EnumSet;

public class StartupRest {
    private int httpPort;
    private StartupApplication startupApplication;

    public StartupRest(int httpPort) {
        this.httpPort = httpPort;
    }

    public void start() {
        configureApplication();
        startRest();
    }

    private void startRest() {
        try {
            Server server = new Server(this.httpPort);
            ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
            configureJersey(servletContextHandler);
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RestServerLaunchFailException(e);
        }
    }

    private void configureApplication() {
        this.startupApplication = new StartupApplication();
        this.startupApplication.init();
        LocatorService.getInstance().registerModule(new LocatorServicesModule());
    }

    private void configureJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages(
                "ca.ulaval.glo4002.rest.resources").register(JacksonFeature.class));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
