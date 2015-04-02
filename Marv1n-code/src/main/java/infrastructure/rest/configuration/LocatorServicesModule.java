package infrastructure.rest.configuration;

import core.person.PersonRepository;
import core.request.RequestRepository;
import core.room.RoomRepository;
import infrastructure.locator.LocatorContainer;
import infrastructure.locator.LocatorModule;
import infrastructure.mail.JavaxMailValidator;
import infrastructure.persistence.PersonRepositoryHibernate;
import infrastructure.persistence.RequestRepositoryInMemory;
import infrastructure.persistence.RoomRepositoryHibernate;

public class LocatorServicesModule implements LocatorModule {
    @Override
    public void load(LocatorContainer container) {
        container.register(RequestRepository.class, new RequestRepositoryInMemory());
        container.register(PersonRepository.class, new PersonRepositoryHibernate(new JavaxMailValidator()));
        container.register(RoomRepository.class, new RoomRepositoryHibernate());
    }
}
