package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.applicationServices.locator.LocatorService;
import ca.ulaval.glo4002.persistence.hibernate.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.*;
import java.io.IOException;

public class EntityManagerContextFilter implements Filter {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entityManagerFactory = LocatorService.getInstance().resolve(EntityManagerFactory.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityManagerProvider.setEntityManager(entityManager);
            chain.doFilter(request, response);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            EntityManagerProvider.clearEntityManager();
        }
    }

    @Override
    public void destroy() {
        entityManagerFactory.close();
    }
}