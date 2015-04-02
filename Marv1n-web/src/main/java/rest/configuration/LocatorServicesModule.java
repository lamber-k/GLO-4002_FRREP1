package rest.configuration;

import locator.LocatorContainer;
import locator.LocatorModule;
import mail.JavaxMailValidator;
import persistence.PersonRepositoryHibernate;
import persistence.RequestRepositoryInMemory;
import persistence.RoomRepositoryHibernate;
import core.person.PersonRepository;
import core.request.RequestRepository;
import core.room.RoomRepository;

public class LocatorServicesModule implements LocatorModule {
    @Override
    public void load(LocatorContainer container) {
        container.register(RequestRepository.class, new RequestRepositoryInMemory());
        container.register(PersonRepository.class, new PersonRepositoryHibernate(new JavaxMailValidator()));
        container.register(RoomRepository.class, new RoomRepositoryHibernate());
    }
}
