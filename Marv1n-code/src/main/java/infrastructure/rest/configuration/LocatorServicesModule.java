package infrastructure.rest.configuration;

import core.person.PersonRepository;
import core.request.RequestRepository;
import core.room.RoomRepository;
import infrastructure.locator.LocatorContainer;
import infrastructure.locator.LocatorModule;
import infrastructure.persistence.PersonRepositoryHibernate;
import infrastructure.persistence.RequestRepositoryInMemory;
import infrastructure.persistence.RoomRepositoryHibernate;

public class LocatorServicesModule implements LocatorModule {
    @Override
    public void load(LocatorContainer container) {
        container.register(RequestRepository.class, new RequestRepositoryInMemory());
        container.register(PersonRepository.class, new PersonRepositoryHibernate());
        container.register(RoomRepository.class, new RoomRepositoryHibernate());
    }
}
