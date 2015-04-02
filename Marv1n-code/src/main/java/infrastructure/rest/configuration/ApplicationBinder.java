package infrastructure.rest.configuration;

import core.request.RequestRepository;
import core.services.RequestService;
import infrastructure.persistence.RequestRepositoryInMemory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RequestRepositoryInMemory.class).to(RequestRepository.class);
        bind(RequestService.class).to(RequestService.class);
    }
}
