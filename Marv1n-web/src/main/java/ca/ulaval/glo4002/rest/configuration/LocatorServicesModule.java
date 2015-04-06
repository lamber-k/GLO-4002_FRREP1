package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.locator.LocatorContainer;
import ca.ulaval.glo4002.locator.LocatorModule;
import ca.ulaval.glo4002.mail.JavaxMailValidator;
import ca.ulaval.glo4002.core.person.PersonRepository;
import ca.ulaval.glo4002.core.request.RequestRepository;
import ca.ulaval.glo4002.core.room.RoomRepository;
import ca.ulaval.glo4002.persistence.hibernate.PersonRepositoryHibernate;
import ca.ulaval.glo4002.persistence.hibernate.RoomRepositoryHibernate;
import ca.ulaval.glo4002.persistence.inMemory.RequestRepositoryInMemory;

public class LocatorServicesModule implements LocatorModule {
    @Override
    public void load(LocatorContainer container) {
        container.register(RequestRepository.class, new RequestRepositoryInMemory());
        container.register(PersonRepository.class, new PersonRepositoryHibernate(new JavaxMailValidator()));
        container.register(RoomRepository.class, new RoomRepositoryHibernate());
    }
}
