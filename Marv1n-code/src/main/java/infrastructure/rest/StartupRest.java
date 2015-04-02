package infrastructure.rest;

import infrastructure.locator.LocatorService;
import infrastructure.rest.configuration.EntityManagerContextFilter;
import infrastructure.rest.configuration.LocatorServicesModule;
import infrastructure.rest.configuration.StartupApplication;
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

    public void start() throws Exception {
        configureApplication();
        startRest();
    }

    private void startRest() throws Exception {
        Server server = new Server(this.httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configureJersey(servletContextHandler);
        server.start();
        server.join();
    }

    private void configureApplication() {
        this.startupApplication = new StartupApplication();
        this.startupApplication.init();
        LocatorService.getInstance().RegisterModule(new LocatorServicesModule());
    }

    private void configureJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages(
                "infrastructure.rest.resources").register(JacksonFeature.class));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
