package ca.ulaval.glo4002.Marv1n.web.rest.configuration;

import ca.ulaval.glo4002.Marv1n.applicationServices.locator.LocatorContainer;
import ca.ulaval.glo4002.Marv1n.applicationServices.locator.LocatorModule;
import ca.ulaval.glo4002.Marv1n.applicationServices.mail.JavaxMailValidator;
import ca.ulaval.glo4002.Marv1n.persistence.PersonRepositoryHibernate;
import ca.ulaval.glo4002.Marv1n.persistence.RequestRepositoryInMemory;
import ca.ulaval.glo4002.Marv1n.persistence.RoomRepositoryHibernate;
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
